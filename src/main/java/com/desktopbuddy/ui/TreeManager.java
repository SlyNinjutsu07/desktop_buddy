package com.desktopbuddy.ui;

import com.desktopbuddy.data.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.nio.file.Files;

public class TreeManager {
    public static DefaultMutableTreeNode buildTree(Folder root){
        DefaultMutableTreeNode head = new DefaultMutableTreeNode(root);

        try (var stream = Files.list(root.getFolderPath())){
            stream.forEach(itemPath -> {
                String itemName = itemPath.getFileName().toString();
                if(Files.isDirectory(itemPath)){
                    if(itemName.equals(".obsidian")) return;
                    head.add(buildTree(new Folder(itemName, itemPath)));
                } else if(itemName.endsWith(".md") || itemName.endsWith(".txt") ){

                    head.add(new DefaultMutableTreeNode(new Note(itemName, itemPath), false));
                }
            });
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not read directory");
        }

        return head;
    }
}
