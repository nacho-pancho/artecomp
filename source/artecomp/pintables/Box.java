package artecomp.pintables;

class Box {

	int x, y, w;

	boolean estancado;

	static int cuenta = 1;

	int num;

	Box(int x, int y) {
	  this.x = x;
	  this.y = y;
	  this.w = 0;
	  this.num = cuenta++;
	  estancado = false;
	}

	void agrandar() {
	  	this.w++;
	}

}
