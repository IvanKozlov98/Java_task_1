import java.io.*;

public class FileRArchiever {
    
    public void decompressData(FileInputStream istream, FileOutputStream ostream)throws IOException
    {
        try(BufferedInputStream bfs = new BufferedInputStream(istream);
            BufferedOutputStream bws = new BufferedOutputStream(ostream)) {
            int symbol,number = 0;
            long countSymbols = 0;
            symbol = bfs.read();
            while(true)
            {
                while((number = bfs.read()) != -1 && number <= 9 )
                    countSymbols = countSymbols * 10 + number;
                //write temp result
                for(int i = 0;i < countSymbols;++i)
                    bws.write(symbol);
                //update
                symbol = number;
                countSymbols = 0;
                //condition exit
                if(number <= 9)
                    break;
            }
        }
    }


}
