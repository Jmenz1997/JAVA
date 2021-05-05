
public class L6Q1 {
	public static void main(String [] args) {
		
		Stack<String> s;

        s = new ArrayStack<String>( 10 );

        for ( int i=0; i<10; i++ ) {
            s.push( "Elem-" + i );
        }
        s.clear();

        

        while ( ! s.isEmpty() ) {
            System.out.println( s.pop() );
        }
        

        for ( int i=0; i<10; i++ ) {
            s.push( "** Elem-" + i );
        }

        while ( ! s.isEmpty() ) {
            System.out.println( s.pop() );
        }
	}
	

}
