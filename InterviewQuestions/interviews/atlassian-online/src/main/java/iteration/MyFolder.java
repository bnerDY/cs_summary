package iteration;

import java.util.Queue;

public class MyFolder <T, U> implements Folder <T, U>{
	public U fold(U u, Queue<T> ts, Function2<T, U, U> function){
		
		if(u == null || ts == null || function == null)
			throw new IllegalArgumentException();
		
// The base point of recursion.
//		if(ts.isEmpty()){
//			return u;
//		}
		
//		return fold(function.apply(ts.poll(), u), ts, function);
//		Tail recursion.
		
		U res = function.apply(ts.poll(),u);
		while(!ts.isEmpty())
		{
			function.apply(ts.poll(),u);
		}
		return res;
	}

}
