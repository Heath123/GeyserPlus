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

package org.geysermc.connector.utils;

import lombok.Getter;

@Getter
public class BoundingBox {
    private double middleX;
    private double middleY;
    private double middleZ;

    private double sizeX;
    private double sizeY;
    private double sizeZ;

    private boolean snapToTop;

    public BoundingBox(double middleX, double middleY, double middleZ, double sizeX, double sizeY, double sizeZ, boolean snapToTop) {
        this.middleX = middleX;
        this.middleY = middleY;
        this.middleZ = middleZ;

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        this.snapToTop = snapToTop;
    }

    public void printDetails() {
        System.out.println("middleX: " + middleX + " middleY: " + middleY + " middleZ: " + middleZ);
        System.out.println("sizeX: " + sizeX + " sizeY: " + sizeY + " sizeZ: " + sizeZ);
        System.out.println("Snap to top: " + snapToTop);
    }

    public void translate(double x, double y, double z) {
        middleX += x;
        middleY += y;
        middleZ += z;
    }

    public boolean checkIntersection(BoundingBox otherBox) {
        return (Math.abs(middleX - otherBox.getMiddleX()) * 2 < (sizeX + otherBox.getSizeX())) &&
                (Math.abs(middleY - otherBox.getMiddleY()) * 2 < (sizeY + otherBox.getSizeY())) &&
                (Math.abs(middleZ - otherBox.getMiddleZ()) * 2 < (sizeZ + otherBox.getSizeZ()));
    }
}
