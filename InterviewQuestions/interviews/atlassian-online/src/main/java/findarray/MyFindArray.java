//Find array
package findarray;

public class MyFindArray implements FindArray{
	public int findArray(int[] array, int[] subArray){
		//Error possible of an array.
		if (array == null || subArray == null || subArray.length > array.length)
        {
            return -1;
        }
		int index = -1; // assume not found
        
        for (int i = 0; i < array.length; i++)
        {
            // Check if the next element of array is same as the first element of subarray
            if (array[i] == subArray[0])
            {
                //check subsequent elements of subarray against the subsequent elements of array
                for (int j = 0; j < subArray.length; j++)
                {
                    //if found, set the index
                    if (i + j < array.length && subArray[j] == array[i + j])
                    {
                        index = i;
                    }
                    else
                    {
                        index = -1;
                        break;
                    }
                }
            }
        }
        return index;
    }
	// Test functionality
	public static void main(String[] args) {
		   int[] a = {4, 9, 3, 7, 8};
		   int[] b = {3, 7};
		   System.out.println(new MyFindArray().findArray(a, b));
		   int[] c = {1, 3, 5};
		   int[] d = {1};
		   System.out.println(new MyFindArray().findArray(c, d));
		   int[] e = {7, 8, 9};
		   int[] f = {8, 9, 10};
		   System.out.println(new MyFindArray().findArray(e, f));
	   }
}
