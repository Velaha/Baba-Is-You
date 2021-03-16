package fr.umlv.BabaIsYou.args;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * This is the class for walk through the files.
 *
 */
public class FilesWalk extends SimpleFileVisitor<Path>{
	
	/**
	 * The ArrayList of Path we find when walking through the files.
	 */
	ArrayList<Path> list_path;
	
	/**
	 * This is the builder of FilesWalk.
	 */
	public FilesWalk() {
		list_path = new ArrayList<>();
	}
	
	 @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		 if(file.toString().contains(".txt")) {
			 this.list_path.add(file);
		 }
        return FileVisitResult.CONTINUE;
    }	 
	 
	 @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
	 
	 @Override	
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }
	 
	 @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
             throws IOException {
         return FileVisitResult.CONTINUE;
     }
	
	 /**
	  * This is a getter for FilesWalk.
	  * @return the list of path
	  */
	public ArrayList<Path> getPath() {
		return this.list_path;
	}
}
