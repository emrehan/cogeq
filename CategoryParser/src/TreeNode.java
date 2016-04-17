import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by saygin on 4/17/2016.
 */
public class TreeNode {
    private ArrayList<TreeNode> children;
    private TreeNode parent;
    private String data;

    public TreeNode( String data, TreeNode parent){
        this.data = data;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public void addChild( TreeNode child){
        children.add( child);
    }


    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static boolean isOnTree( String value, TreeNode root){
        if( root.getData().equals( value) ){
            return true;
        }
        else{
            boolean found = false;
            for (int i = 0; i < root.getChildren().size(); i++) {
                found |= isOnTree( value, root.getChildren().get(i));
            }
            return found;

        }
    }

    public static void printTree( TreeNode root, int indentation, BufferedWriter bw) throws IOException {
        for (int i = 0; i < indentation; i++) {
            bw.write("\t");
        }
        bw.write(root.data);
        bw.write("\n");
        for (int i = 0; i < root.getChildren().size(); i++) {
            printTree(root.getChildren().get(i), indentation + 1, bw);
        }
    }

    public static TreeNode getTreeNode( String value, TreeNode root ){
        if( root.getData().equals(value)){
            return root;
        }
        TreeNode retVal = null;
        for (int i = 0; i < root.getChildren().size(); i++) {
            if( getTreeNode( value, root.getChildren().get(i)) != null){
                retVal = getTreeNode( value, root.getChildren().get(i));
                break;
            }
        }
        return retVal;
    }
}
