/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/Geyser
 *
 */

package org.geysermc.connector.network.translators.world.collision;

import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import org.geysermc.connector.network.translators.item.ItemEntry;
import org.geysermc.connector.network.translators.world.collision.translators.*;
import org.geysermc.connector.network.session.GeyserSession;
import org.geysermc.connector.network.translators.world.block.BlockTranslator;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollisionTranslator {
    private Map<Pattern, Class> regexMap = new HashMap<>();

    public void init() {
        Reflections ref = new Reflections("org.geysermc.connector.network.translators.world.collision.translators");
        for (Class<?> clazz : ref.getTypesAnnotatedWith(CollisionRemapper.class)) {
            String regex = clazz.getAnnotation(CollisionRemapper.class).regex();
            System.out.println(regex);
            regexMap.put(Pattern.compile(regex), clazz);
        }
    }

    public BlockCollision getCollision(GeyserSession session, BlockState block, int x, int y, int z) {

        String blockID = BlockTranslator.getJavaIdBlockMap().inverse().get(block);
        String blockName = blockID.split("\\[")[0].replace("minecraft:", "");
        System.out.println(blockID);

        for (Map.Entry<Pattern, Class> entry : regexMap.entrySet()) {
            if (entry.getKey().matcher(blockName).find()) {
                try {
                    return (BlockCollision) entry.getValue().newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        /* if (blockID.contains("_slab")) {
            if (blockID.contains("type=bottom")) {
                return new SlabCollision(x, y, z);
            } else {
                // No correction is needed for top slabs
                // TODO: Add collision
                return null;
            }
        } else if (blockID.contains("_stairs")) {
            if (blockID.contains("facing=west")) {
                return new StairCollision(x, y, z, "west");
            }
        } else if (blockID.contains("sandstone")) {
            return new SolidCollision(x, y, z);
        }
        return null; */
        return null;
    }
}