package artecomp.base;


public class FillMask {

  boolean[][] mask;
  int m,n;

  public FillMask(int _m, int _n) {
    // invalid sizes
    if (m <= 0) m = 1;
    if (n <= 0) n = 1;
    m = _m;
    n = _n;
    mask = new boolean[m][n];
    clear();
  }

  public void clear() {
    for (int i=0; i < mask.length; i++) {
      for (int j=0; j < mask[i].length; j++)
        mask[i][j] = false;
    }
	}

  public boolean getVal(int i, int j) {
    return mask[i][j];
  }

  public int getRows() { return m; }
  
  public int getCols() { return n; }
  

  public void  setVal(int i, int j, boolean v) {
    mask[i][j] = v;
  }

  public Cursor cursor() {
    return new Cursor();
  }

  public Cursor cursor(int i, int j) {
    return new Cursor(i,j);
  }

  public class Cursor {
    int i,j;

    public Cursor(int _i, int _j) {
      goTo(_i,_j);
    }

    public Cursor() {
      this(0,0);
    }

    public void goTo(int _i, int _j) {
      this.i = _i;
      this.j = _j;
    }

    public int getRow() { return i; }

    public int getCol() { return j; }


    public int down() {
        return ++i; 
    }

    public int down(int d) {
      i += d; return i;
    }

    public int up() {
        return --i;
    }

    public int up(int d) {
      i -= d; return i;
    }


    public int right() {
      return ++j;
    }

    public int right(int d) {
    	j += d;
    	return d;
    }


    public int left() {
    	return --j;
    }

    public int left(int d) {
    	j -= d; return j;
    }

   public boolean canGoUp() {
    return i > 0;
   }

   public boolean canGoDown() {
    return i < (FillMask.this.m-1);
   }

   public boolean canGoLeft() {
     return j > 0;
   }

   public boolean canGoRight() {
     return j < (FillMask.this.n-1);
   }

   public boolean getVal() { return mask[i][j]; }

   public void setVal(boolean v) { mask[i][j]=v; }

   public String toString() {
      return "(" + i + "," + j + ")";
   }

  } // class Cursor

  public String toString() {
    StringBuffer sb = new StringBuffer();
    Cursor c = cursor();
    do {
      c.goTo(c.getRow(),0);
      do {
	System.out.flush();
        sb.append(c.getVal() ? '1' : '0');
      } while (c.canGoRight());
      sb.append('\n');
    } while (c.canGoDown());
    return sb.toString();
  }

  public static void main(String[] args) {
    FillMask  fm = new FillMask(3,66);
    fm.setVal(0,1,true);
    fm.setVal(1,65,true);
    fm.setVal(2,64,true);
    System.out.println(fm);
  }
}
