import sun.reflect.generics.tree.Tree;

import java.io.*;
import java.time.temporal.Temporal;
import java.util.Scanner;

/**
 * Created by saygin on 4/17/2016.
 */
public class CategoryParser {

    public static void main(String[] args) {
        TreeNode root = new TreeNode( "Bosh", null );
        Scanner scan = new Scanner(System.in);
        try{
            BufferedReader br = new BufferedReader(new FileReader( "C:\\Users\\saygin\\Downloads\\dataset_TIST2015\\dataset_TIST2015_POIs.txt"));

            String line = br.readLine();
            int count = 0;
            while( line != null ) {
                String[] tokens = line.split( "\t");
                String currentNode = tokens[3];
                TreeNode childNode;
                childNode = new TreeNode( currentNode, null);
                boolean finished = false;
                boolean isProcessed = false;
                while ( !TreeNode.isOnTree( currentNode, root)) {
                    isProcessed = true;
                    System.out.println( "What is the parent of " + currentNode);
                    currentNode = scan.nextLine();
                    currentNode = currentNode.trim();
                    while( currentNode.equals("")){
                        currentNode = scan.nextLine();
                        currentNode = currentNode.trim();
                    }

                    if( currentNode.equalsIgnoreCase( "1")){
                        root.addChild( childNode);
                        childNode.setParent(root);
                        finished = true;
                        break;
                    }
                    else {
                        if( !TreeNode.isOnTree( currentNode, root)) {
                            TreeNode parentNode = new TreeNode(currentNode, null);
                            childNode.setParent(parentNode);
                            parentNode.addChild(childNode);
                            childNode = parentNode;
                        }
                    }
                }
                if( !finished && isProcessed){
                    TreeNode node = TreeNode.getTreeNode(currentNode, root);
                    node.addChild(childNode);
                    childNode.setParent(node);
                }

                line = br.readLine();
                count++;
            }
            BufferedWriter bw = new BufferedWriter( new FileWriter( "tree.txt"));
            TreeNode.printTree( root, 0, bw);
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
