package flatten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFlattenTree<T> implements FlattenTree<T>
{
	//The final list returns left, middle, right in order using ArrayList.
    public List<T> flattenInOrder(Tree<T> tree)
    {
        if (tree == null)
            throw new IllegalArgumentException("Tree cannot be null.");
        if (tree.get().isLeft())
            return Arrays.asList(tree.get().ifLeft(goLeftFunction));
        else
            return tree.get().ifRight(goRightFunction);
    }

    private final Function<T, T> goLeftFunction = new Function<T, T>()
    {
        @Override
        public T apply(T p)
        {
            return p;
        }
    };

    private final Function<Triple<Tree<T>>, List<T>> goRightFunction = new Function<Triple<Tree<T>>, List<T>>()
    {
        @Override
        public List<T> apply(Triple<Tree<T>> p)
        {
            List<T> res = new ArrayList<T>();
            res.addAll(flattenInOrder(p.left()));
            res.addAll(flattenInOrder(p.middle()));
            res.addAll(flattenInOrder(p.right()));
            return res;
        }
    };
}
