public class MyArrayHandler {

    /**a) переворачивает массив int[]: {1,2,3,7} -> {7,3,2,1}

     б) считает среднее, минимум, максимум и медиану в массиве {2,2,4,4} -> 3.0, 2, 4, 3.0

     г) ищет в массиве int arr[]={1,2,3,2,1,3,1,2,6,7,8,9,10,11,0,-1} самую длинную непрерывную возрастающую подпоследовательность: {1,2,6,7,8,9,10,11}.

     в) самостоятельно реализовать пузырьковую сортировку*/

 //переворачивает массив int[]: {1,2,3,7} -> {7,3,2,1}
    public void reverseArray(int[] array)
    {
        int buffer;
        int len = array.length;
        for(int i = 0;i < len / 2;++i) {
            buffer = array[i];
            array[i] = array[len - 1 - i];
            array[len - 1 - i] = buffer;
        }
    }

    public double middleArray(int[] array)
    {
        //check on available
        int len = array.length;
        if(0 == len)
            return 0.0;
        //operation
        int sum = 0;
        for(int el:array)
            sum+=el;
        return (double)sum / (double)len;
    }

    public int minArray(int[] array)
    {
        //check on available
        int len = array.length;
        if(0 == len)
            return 0;
        //operation
        int min = array[0];
        for(int el:array)
            if(el < min)
                min = el;
        return min;
    }

    public int maxArray(int[] array)
    {
        //check on available
        int len = array.length;
        if(0 == len)
            return 0;
        //operation
        int max = array[0];
        for(int el:array)
            if(el > max)
                max = el;
        return max;
    }

    public int medianArray(int[] array)
    {
        //check on available
        int len = array.length;
        if(0 == len)
            return 0;
        //operation
        int[] arrayCopy = array.clone();
        sortArray(arrayCopy);
        return arrayCopy[arrayCopy.length / 2];
    }


   //strictly increase
    public int[] maxIncreasingSequenceArray(int[] array)
    {
        //check on available
        int lenArray = array.length;
        if(0 == lenArray)
            return null;
        //operation
        int currentLen = 1,maxLen = 1;
        int leftBoundary = 0,rightBoundary = 0,currentLeftBoundary = 0;
        for(int i = 1;i < lenArray;++i)
        {
            if(array[i] > array[i - 1])
                currentLen++;
            else
            {
                //update maxLen
                if(currentLen > maxLen) {
                    leftBoundary = currentLeftBoundary;
                    rightBoundary = i - 1;
                    maxLen = currentLen;
                }
                currentLeftBoundary = i;
                currentLen = 1;
            }
        }
        //update maxLen
        if(currentLen > maxLen) {
            leftBoundary = currentLeftBoundary;
            rightBoundary = lenArray - 1;
            maxLen = currentLen;
        }
        //rightBoundary >= leftBoundary
        int[] res = new int[rightBoundary - leftBoundary + 1];
        for(int i = leftBoundary;i <= rightBoundary;++i)
            res[i - leftBoundary] = array[i];
        return res;
    }

    //sort
    public void sortArray(int[] array)
    {
        int buffer;
        int lenArray = array.length;
        for(int i = 0;i < lenArray - 1;++i)
            for (int j = lenArray - 2;j >= i;--j)
                if(array[j] < array[j + 1])
                {
                    buffer = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = buffer;
                }
    }
}
