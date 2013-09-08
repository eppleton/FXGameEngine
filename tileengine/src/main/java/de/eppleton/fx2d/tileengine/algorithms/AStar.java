/**
 * This file is part of FXGameEngine A Game Engine written in JavaFX Copyright
 * (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. Look for COPYING file in the top folder. If not, see
 * http://opensource.org/licenses/GPL-2.0.
 *
 * For alternative licensing or use in closed source projects contact Anton
 * Epple
 * <info@eppleton.de>
 */
package de.eppleton.fx2d.tileengine.algorithms;

import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import java.util.*;

public class AStar {

    public static PathNode getPath(TileMap map, TileMapLayer layer, AStarTile start, AStarTile goal) {

        boolean[][] playField = new boolean[map.getWidth()][map.getHeight()];
        for (int x = 0; x < playField.length; x++) {
            boolean[] bs = playField[x];
            for (int y = 0; y < bs.length; y++) {
                int idx = x + (y * map.getWidth());
                bs[y] = layer.getGid(idx) == 0;
            }
        }

        PriorityQueue<PathNode> openList = new PriorityQueue<PathNode>();
        List<PathNode> closedList = new ArrayList<PathNode>();
        PathNode stn = new PathNode(null, 0, 0, start.x, start.y);
        openList.add(stn);
        while (!openList.isEmpty()) {
            PathNode q = openList.poll();
            List<PathNode> successors = new ArrayList<PathNode>();
            int qx = q.getX();
            int qy = q.getY();
            addSuccessor(playField, qx - 1, qy, q, goal, successors);
            addSuccessor(playField, qx, qy - 1, q, goal, successors);
            addSuccessor(playField, qx + 1, qy, q, goal, successors);
            addSuccessor(playField, qx, qy + 1, q, goal, successors);
            for (PathNode candidate : successors) {
                if (candidate.x == goal.x && candidate.y == goal.y) {
                    return candidate;
                }
                boolean add = true;
                if (alreadyFound(candidate, closedList)) {
                    continue;
                }
                if (alreadyFound(candidate, openList)) {
                    add = false;
                }
                if (add) {
                    openList.add(candidate);
                }
            }
            closedList.add(q);
        }
        return null;
    }

    public static void addSuccessor(boolean[][] playField, int qx, int qy, PathNode q, AStarTile goal, List<PathNode> successors) {

        if (qx < 0 || qy < 0 || qx >= playField.length || qy >= playField[qx].length) {
            return;
        }

        if (playField[qx][qy]) {
            PathNode n = new PathNode(q, qx, qy);
            n.g = g(n);
            n.h = h(n, goal);
            n.f = n.g + n.h;
            successors.add(n);
        }
    }

    private static boolean alreadyFound(PathNode n, Collection<PathNode> l) {
        for (PathNode no : l) {
            if (no.getX() == n.getX() && no.getY() == n.getY() && no.getF() <= n.getF()) {
                return true;
            }
        }
        return false;
    }

    private static float g(PathNode n) {
        PathNode p = n.getParent();
        return p.g + 1;
    }

    private static float h(PathNode act, AStarTile goal) {
        int distX = Math.abs(act.getX() - goal.x);
        int distY = Math.abs(act.getY() - goal.y);
        float ret = (float) Math.sqrt(distX * distX + distY * distY);
        return ret;
    }

    public static class AStarTile {

        int x, y;

        public AStarTile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class PathNode implements Comparable<PathNode> {

        private PathNode parent;
        private float f, g, h;

        public PathNode getParent() {
            return parent;
        }
        private int x, y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        float getF() {
            return f;
        }

        private PathNode(PathNode parent, float g, float h, int x, int y) {
            this.parent = parent;
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }

        private PathNode(PathNode parent, int x, int y) {
            this(parent, 0, 0, x, y);
        }

        @Override
        public int compareTo(PathNode o) {
            return f > o.f ? 1 : f == o.f ? 0 : -1;
        }
    }
}