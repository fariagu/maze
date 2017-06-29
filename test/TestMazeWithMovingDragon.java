import logic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMazeWithMovingDragon {

    char[][] m0 = {
            {'X', 'X', 'X', 'X', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X'}
    };

    char[][] m1 = {
            {'X', 'X', 'X'},
            {'X', ' ', 'X'},
            {'X', ' ', 'X'},
            {'X', ' ', 'S'},
            {'X', 'X', 'X'}
    };

    char[][] m2 = {
            {'X', 'X', 'X', 'X', 'X'},
            {'X', ' ', ' ', ' ', 'S'},
            {'X', 'X', 'X', 'X', 'X'}
    };

    char[][] m3 = {
            {'X', 'X', 'X', 'X'},
            {'X', ' ', ' ', 'S'},
            {'X', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X'}
    };


    @Test
    public void testDragonMovesDown() {
        MazeBuilder maze = new MazeBuilder(m1);
        //Hero h = new Hero(1, 3, maze);
        Dragon d = new Dragon(1, 1, maze);
        Point p = new Point(1, 1);

        boolean dragonMoves = false;
        while (!dragonMoves) {
            d.move(maze);
            if (p.getX() != d.getX()) {
                dragonMoves = true;

            } else {
                dragonMoves = false;
            }

        }
        assertEquals(d.getX(), 2);
    }

    @Test
    public void testDragonMovesUp() {
        MazeBuilder maze = new MazeBuilder(m1);
        //Hero h = new Hero(1, 3, maze);
        Dragon d = new Dragon(3, 1, maze);
        Point p = new Point(3, 1);

        boolean dragonMoves = false;
        while (!dragonMoves) {
            d.move(maze);
            if (p.getX() != d.getX()) {
                dragonMoves = true;

            } else {
                dragonMoves = false;
            }

        }
        assertEquals(d.getX(), 2);
    }

    @Test
    public void testDragonMovesRight() {
        MazeBuilder maze = new MazeBuilder(m2);
        //Hero h = new Hero(1, 3, maze);
        Dragon d = new Dragon(1, 1, maze);
        Point p = new Point(1, 1);

        boolean dragonMoves = false;
        while (!dragonMoves) {
            d.move(maze);
            if (p.getY() != d.getY()) {
                dragonMoves = true;

            } else {
                dragonMoves = false;
            }

        }
        assertEquals(d.getY(), 2);
    }

    @Test
    public void testDragonMovesLeft() {
        MazeBuilder maze = new MazeBuilder(m2);
        //Hero h = new Hero(1, 3, maze);
        Dragon d = new Dragon(1, 3, maze);
        Point p = new Point(1, 3);

        boolean dragonMoves = false;
        while (!dragonMoves) {
            d.move(maze);
            if (p.getY() != d.getY()) {
                dragonMoves = true;

            } else {
                dragonMoves = false;
            }

        }
        assertEquals(d.getY(), 2);
    }

    @Test
    public void testDragonOverSword() {
        MazeBuilder maze = new MazeBuilder(m1);
        //Hero h = new Hero(1, 3, maze);
        Dragon d = new Dragon(1, 1, maze);
        Sword s = new Sword(2, 1, maze);

        boolean overlapping = false;
        while (!overlapping) {
            d.move(maze);
            s.dragonOverlap(d, maze);
            if (maze.getMaze(s.getX(), s.getY()) == 'F') {
                overlapping = true;
                assertEquals(d.getX(), s.getX());
                assertEquals(d.getY(), s.getY());
            } else {
                overlapping = false;
            }
        }
    }

    @Test
    public void testDragonSleep() {
        MazeBuilder maze = new MazeBuilder(m0);
        Dragon d = new Dragon(2, 2, maze);
        int x = 2, y = 2;
        boolean dragonSleeps = false;
        d.moveOrSleep(maze);
        d.moveOrSleep(maze);
        d.moveOrSleep(maze);
        d.moveOrSleep(maze);
        while (!d.isSleeping()) {
            d.moveOrSleep(maze);
            if (!d.isSleeping()) {
                x = d.getX();
                y = d.getY();
            }
        }
        assertEquals(d.getX(), x);
        assertEquals(d.getY(), y);
        assertEquals(d.isSleeping(), true);
    }

    @Test
    public void testDragonKillUp() {
        MazeBuilder maze = new MazeBuilder(m1);
        Dragon d = new Dragon(1, 1, maze);
        Hero h = new Hero(3, 1, maze);
        boolean heroAlive = true;

        while (heroAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!h.isAlive()) {
                heroAlive = false;
            }
        }
        assertEquals(false, h.isAlive());
        assertEquals(true, d.isAlive());
    }

    @Test
    public void testDragonKillDown() {
        MazeBuilder maze = new MazeBuilder(m1);
        Dragon d = new Dragon(3, 1, maze);
        Hero h = new Hero(1, 1, maze);
        boolean heroAlive = true;

        while (heroAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!h.isAlive()) {
                heroAlive = false;
            }
        }
        assertEquals(false, h.isAlive());
        assertEquals(true, d.isAlive());
    }

    @Test
    public void testDragonKillLeft() {
        MazeBuilder maze = new MazeBuilder(m2);
        Dragon d = new Dragon(1, 1, maze);
        Hero h = new Hero(1, 3, maze);
        boolean heroAlive = true;

        while (heroAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!h.isAlive()) {
                heroAlive = false;
            }
        }
        assertEquals(false, h.isAlive());
        assertEquals(true, d.isAlive());
    }

    @Test
    public void testDragonKillRight() {
        MazeBuilder maze = new MazeBuilder(m2);
        Dragon d = new Dragon(1, 3, maze);
        Hero h = new Hero(1, 1, maze);
        boolean heroAlive = true;

        while (heroAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!h.isAlive()) {
                heroAlive = false;
            }
        }
        assertEquals(false, h.isAlive());
        assertEquals(true, d.isAlive());
    }

    @Test
    public void testDragonDieUp() {
        MazeBuilder maze = new MazeBuilder(m1);
        Dragon d = new Dragon(1, 1, maze);
        Hero h = new Hero(3, 1, maze);
        h.setArmed(true);
        boolean dragonAlive = true;

        while (dragonAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!d.isAlive()) {
                dragonAlive = false;
            }
        }
        assertEquals(true, h.isAlive());
        assertEquals(false, d.isAlive());
    }

    @Test
    public void testDragonDieDown() {
        MazeBuilder maze = new MazeBuilder(m1);
        Dragon d = new Dragon(3, 1, maze);
        Hero h = new Hero(1, 1, maze);
        h.setArmed(true);
        boolean dragonAlive = true;

        while (dragonAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!d.isAlive()) {
                dragonAlive = false;
            }
        }
        assertEquals(true, h.isAlive());
        assertEquals(false, d.isAlive());
    }

    @Test
    public void testDragonDieLeft() {
        MazeBuilder maze = new MazeBuilder(m2);
        Dragon d = new Dragon(1, 1, maze);
        Hero h = new Hero(1, 3, maze);
        h.setArmed(true);
        boolean dragonAlive = true;

        while (dragonAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!d.isAlive()) {
                dragonAlive = false;
            }
        }
        assertEquals(true, h.isAlive());
        assertEquals(false, d.isAlive());
    }

    @Test
    public void testDragonDieRight() {
        MazeBuilder maze = new MazeBuilder(m2);
        Dragon d = new Dragon(1, 3, maze);
        Hero h = new Hero(1, 1, maze);
        h.setArmed(true);
        boolean dragonAlive = true;

        while (dragonAlive) {
            d.moveOrSleep(maze);
            d.fight(h, maze);
            if (!d.isAlive()) {
                dragonAlive = false;
            }
        }
        assertEquals(true, h.isAlive());
        assertEquals(false, d.isAlive());
    }
}