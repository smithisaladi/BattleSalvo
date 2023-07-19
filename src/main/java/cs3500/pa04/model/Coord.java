package cs3500.pa04.model;

import java.util.Objects;

/**
 * Represents a Coordinate
 */
public class Coord {

  private int width = -1;
  private int height = -1;

  /**
   * The Constructor
   *
   * @param width x coordinate
   * @param height y coordinate
   * @throws Exception if the coordinates aren't valid
   */
  public Coord(int width, int height) throws Exception {
    if (width < 0 || height < 0) {
      throw new Exception("Invalid Co-ordinates <" + width + "> <" + height + ">");
    }
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void print() {
    System.out.println("Co-ordinates : <" + width + "> <" + height + ">");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return width == coord.width && height == coord.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }
}