package flatten;

public class MyFlattenTreeTest
{
    public static void main(String[] args)
    {
        Tree<Integer> childNode = Tree.Node.tree(5, 4, 9);
        Tree<Integer> rootNode = new Tree.Node<Integer>(Tree.Leaf.leaf(1), childNode, Tree.Leaf.leaf(6));
        MyFlattenTree<Integer> flattenTree = new MyFlattenTree<Integer>();
        System.out.println("Flattened tree: " + flattenTree.flattenInOrder(rootNode));
    }
}