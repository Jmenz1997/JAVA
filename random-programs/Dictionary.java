
public class Dictionary implements Map<String, Integer> {
	private final static int INITIAL_CAPACITY=10;
	private final static int INCREMENT=5;
	private int count;
	private Pair[] elems;
	
	public int getCount() {
		return count;
	}
	public int getCapacity() {
		return elems.length;
	}
	public Dictionary() {
		elems=new Pair[INITIAL_CAPACITY];
		count=0;	
		
	}
	public Integer get(String key) {
		for (int i=count-1;i>-1;i--) {
			if(elems[i].getKey().equals(key)) {
				return elems[i].getValue();
			}
		}

		return null ;
	}
	public boolean contains(String key) {
		for(int i=count-1;i<elems.length;i++) {
			if (elems[i].getKey()==key) {
				return true;
			}
			
		}
		
		return false;
	}
	
	private void incrementedCapacity() {
		Pair[] tmp=new Pair[count];
		System.arraycopy(elems, 0, tmp, 0,count );
		this.elems=new Pair[INCREMENT+getCapacity()];
		System.arraycopy(tmp, 0, elems, 0, count);
	}
	public void put(String key, Integer value) {
		Pair element=new Pair(key,value);
		if (count==getCapacity()) {
			incrementedCapacity();
		}
		elems[count++]=element;
	}

	@Override
	public void replace(String key, Integer value) {
		for (int i=0;i<=elems.length;i++) {
			if (elems[i].getKey()==key) {
				elems[i].setValue(value);
			}
		}
		

	}

	
	public Integer remove(String key) {
		Integer v=0;
		for (int i=count-1;i>-1;i--) {
			if(elems[i].getKey()==key) {
				v=elems[i].getValue();
				
			}
			
		}
		
		
		return v;
	}
	
	public String toString() {
	      String res;
	      res = "Dictionary: {elems = [";
	      for (int i = count-1; i >= 0 ; i--) {
	          res += elems[i];
	          if(i > 0) {
	              res += ", ";
	          }
	      }
	      return res +"]}";
	}

}
