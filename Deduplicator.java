import com.sun.istack.internal.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;


class MyComparator implements Comparator<List<Path>>
{
    @Override
    public int compare(List<Path> listOfPath1, List<Path> listOfPath2) {
        long comp = -1;
        try {
            if(listOfPath1.isEmpty() || listOfPath2.isEmpty())
                throw new RuntimeException();
            comp = Deduplicator.compare(listOfPath1.get(0),listOfPath2.get(0));
        }catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return (comp > 0) ? 1 :
                (comp < 0) ? -1:
                 0;
    }
};




public class Deduplicator {

    public static long compare(Path file1,Path file2)throws IOException
    {
        //better throw exception ?
        if(Files.isDirectory(file1) || Files.isDirectory(file2))
            return 0;
        //
        long diff = Files.size(file1) - Files.size(file2);
        if(diff != 0)
            return diff;
        //otherwise
        int byte1,byte2;
        try(
                BufferedInputStream f1 = new BufferedInputStream(Files.newInputStream(file1));
                BufferedInputStream f2 = new BufferedInputStream(Files.newInputStream(file2));
        ){
            if(Files.isReadable(file1) && Files.isReadable(file2)) {
                //size files are same
                while ((byte1 = f1.read()) != -1)//read byte from file1
                {
                    byte2 = f2.read();//then read byte from file2
                    diff = byte1 - byte2;
                    if (diff != 0)
                        return diff;
                }
            }
            else
                throw new IOException();//???????
        }
        return 0;
    }


    public static List<Path> getListFilesCurrentDirectory(Path dir)throws IOException
    {
        Stream<Path> pathStream = Files.walk(dir);
        List<Path> list = new LinkedList<>();
        //добавляем только файлы, игнорируя папки
        pathStream.forEach((f1) -> { if(!Files.isDirectory(f1)) list.add(f1);});
        return list;
    }

    //this function build tree
    private static Set<List<Path>> buildMyTreeSet(Path path)throws IOException
    {
        Set<List<Path>> setOfPaths = new TreeSet<>(new MyComparator());
        List<Path> listAllPath = getListFilesCurrentDirectory(path);
        //временный список в котором всегда будем содержаться только один элемент
        List<Path> temp = new LinkedList<>(),tempAB = new LinkedList<>();
        List<Path> classEquals;
        for(Path currentPath:  listAllPath) {
             temp.add(currentPath);
             //
             classEquals = ((TreeSet<List<Path>>) setOfPaths).ceiling(temp);
             //если похожих элементов нет - то создаем новый класс эквивалентности
             if(null == classEquals || compare(classEquals.get(0),currentPath) != 0) {
                 //создаем новый класс эквивалентности, состящий из одного элемента
                 List<Path> addedClassEquals = new LinkedList<>();
                 addedClassEquals.add(currentPath);
                 //и добавляем его во множество классов эквивалентности
                 setOfPaths.add(addedClassEquals);
             }
             else //иначе - добавляем path в соответствующий класс эквивалентности
                 classEquals.add(currentPath);
             //продолжаем поддерживать наличие только одного элемента во временном листе
             temp.remove(currentPath);
        }
        return setOfPaths;
    }

    public static void writeDublicates(Path path)throws IOException
    {
        Set<List<Path>> setOfPaths = buildMyTreeSet(path);
        for (List<Path> listPath : setOfPaths)
        {
            if(listPath.size() > 1) //если есть дубликаты
            {
                for(Path dublPath: listPath)
                    System.out.print(dublPath.getFileName() + "  ");
                System.out.println("are duplicates");
            }
        }
    }
}
