/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.robustaweb.library.commons.exception.FileException;

/**
 * FileUtilities brings frequently used funcions, to be used in a standard Windows/linux or in the Clouds
 * 
 * <p>todo : an adaptation for Google AppEngine is on its way, so that it will be transparent for the programmer to use saveFile and readFile
 * in a Linux, Windows & AppEngine environment
 * </p>
 *
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class FileUtils {

    static boolean inClouds = false;

    /**
     * Reads a file line after line.
     * @param path Full path of the file ('c:/webapp/data.xml' or '/var/webapp/data.xml')
     * @return The content of the file.
     * @throws java.io.FileNotFoundException
     */
    public static String readFile(String path) throws FileNotFoundException {

        FileReader reader = new FileReader(path);
        BufferedReader buffReader = new BufferedReader(reader);

        StringBuilder text = new StringBuilder();

        if (buffReader != null) {
            try {
                String tempLine;
                tempLine = buffReader.readLine();
                while (tempLine != null) {
                    text.append(tempLine).append("\n");
                    // System.out.println("templine :"+tempLine);
                    tempLine = buffReader.readLine();
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.toString());
            } finally {
                try {
                    buffReader.close();
                } catch (IOException e) {
                    System.err.println(
                            "Erreur closing file: " + e.toString());
                }
            }
        }

        return text.toString();

    }

    /**
     * @todo2 : It seems to save in only one line ! need to be tested
     * @param path Full path of the file ('c:/webapp/data.xml' or '/var/webapp/data.xml')
     * @param content Content to be saved in the file
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveFile(String path, String content) throws FileNotFoundException, IOException {

        File f = new File(path);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fstream = new FileWriter(path);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(content);
        //Close the output stream

        fstream.flush();
        out.close();
        fstream.close();


    }

    /**
     * Create a directory
     * @param rootPath directory or mount point containing your future directory
     * @param directoryName name of the future directory
     * @throws robusta.commons.exceptions.FileException
     */
    public static void createDirectory(String rootPath, String directoryName) throws FileException {
        File f = new File(rootPath + "/" + directoryName);
        boolean result;
        if (f.exists()) {
            throw new FileException("Directory already exists");
        } else {
            result = f.mkdir();
            if (!result) {
                throw new FileException("Directory could not be created for unknown reason");
            }
        }
    }

    /**
     * <p>Delete a directory. It first delete recursively all subdirectories, then all files of this directory, then the directory.</p>
     * <p>The code was partially found in the net - it's very likely to be public domain.</p>
     * @param path java.io.File representation of the directory 
     * @throws FileException if the path does not exist or if the directory can't be deleted for any unknown  reason.
     */
    static public void deleteDirectory(File path) throws FileException {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    //recursive mode
                    deleteDirectory(files[i]);
                } else {
                    ///      System.out.println("FILE TO DELETE : " + files[i].getPath() + " - " + files[i].delete());
                }
            }
        } else {
            throw new FileException("The path :'" + path + "' does't exist ");
        }

        if (!path.delete()) {
            throw new FileException("Could not delete path " + path + "!");

        } else {
            //nothing to do, it's ok
        }

    }

    /**
     * Delete the file, that is NOT a directory
     * @param path
     * @return
     * @throws robusta.commons.exceptions.FileException
     */
    static public boolean deleteFile(String path) throws FileException {

        File f = new File(path);
        if (f == null || !f.exists()) {
            throw new FileException("Can't find the file at path : " + path);
        } else {
            return f.delete();
        }

    }

    /**
     * returns true if the Path or File exists
     * @param path
     * @return
     */
    public static boolean pathExists(String path) {

        File f = new File(path);
        return f.exists();

    }

    /**
     * Read an InputStream and returns a String. Notice that it will close the InputStream.
     * @param inputStream InputStrem
     * @return
     * @throws java.io.IOException if it's impossible to read tje InputStram
     * @throws IllegalArgumentException if is is null
     */
    public static String readInputStream(InputStream inputStream) throws IOException {

        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream is null");
        }

        StringBuffer buffer = new StringBuffer();


        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String str = "";
        boolean firstLine = true;

        while (str != null) {
            if (!firstLine) {
                buffer.append("\n");
            }
            str = in.readLine();
            if (str != null) {
                buffer.append(str);
            }
            firstLine = false;
        }
        in.close();
        inputStream.close();
        return buffer.toString();
    }
}





