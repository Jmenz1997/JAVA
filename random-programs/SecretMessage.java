import java.io.*;
public class SecretMessage {
	public static void encrypt( String inputFilem, String outputFile, int key ) throws IOException, FileNotFoundException {

        InputStreamReader input = null;
		OutputStreamWriter out = null;
		//Input init
		FileInputStream f = new FileInputStream(inputFilem);
		input=new InputStreamReader(f);
		//Output 
		OutputStream o=new FileOutputStream(outputFile);
		
		out= new OutputStreamWriter(o);
		int i;
		while((i=input.read())!=-1) {
			char c=(char) (key+i);
			System.out.print(c);
			
			
		}
		
		if (inputFilem==null) {
			throw new UnsupportedOperationException("SecretMessage encrypt not implemented");
		}
        
        
        
        
        

    }
	
	public static void decrypt( String inputFilem, String outputFile, int key ) throws IOException, FileNotFoundException {
		 InputStreamReader input = null;
		 OutputStreamWriter out = null;
		 //Input init
		 FileInputStream f = new FileInputStream(inputFilem);
		 input=new InputStreamReader(f);
			//Output 
		 OutputStream o=new FileOutputStream(outputFile);
			
		 out= new OutputStreamWriter(o);
		 int i;
		 while((i=input.read())!=-1) {
				char c=(char) (i-key);
				System.out.print(c);
				
				
			}
		 if(inputFilem==null) {
			 throw new UnsupportedOperationException("SecretMessage decrypt not implemented");
		 }
			
		
        
        // YOUR CODE HERE (remove the exception)

    }

    public static void main( String[] args ) {

        if ( args.length != 4 ) {
            System.out.println( "Usage: java SecretMessage [encrypt|decrypt] inputFile OutputFile key" );
            System.exit( 0 );
        }

		if(args[0].equals("encrypt")){

		
            try {
                encrypt( args[1],args[2], Integer.parseInt(args[3]));
            } catch ( FileNotFoundException e ) {
                System.err.println( "File not found: "+e.getMessage() );
            } catch (IOException e) {
                System.err.println( "Cannot read/write file: "+e.getMessage() );
            }
		}
		else if(args[0].equals("decrypt")){

		
            try {
                decrypt( args[1],args[2], Integer.parseInt(args[3]));
            } catch ( FileNotFoundException e ) {
                System.err.println( "File not found: "+e.getMessage() );
            } catch (IOException e) {
                System.err.println( "Cannot read/write file: "+e.getMessage() );
            }
        }
		else{
			System.out.println( "Usage: java SecretMessage [encrypt|decrypt] inputFile OutputFile key" );
            System.exit( 0 );
		}
        
    }

}
