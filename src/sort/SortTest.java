package sort;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class SortTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SortTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr = {6,9,2,4,5,3,1,8,7};
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		bubbleSort(arr);
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		
		arr = new int[]{6,9,2,4,5,3,1,8,7};
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		selectSort(arr);
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		
		arr = new int[]{6,9,2,4,5,3,1,8,7};
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		insertSort(arr);
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		
		arr = new int[]{6,9,2,4,5,3,1,8,7};
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		quickSort(arr, 0, arr.length-1);
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		
		arr = new int[]{62,95,22,42,53,31,11,81,72};
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		quickSort(arr, 0, arr.length-1);
		logger.info("main(String[]) - int[] arr=" + Arrays.toString(arr)); //$NON-NLS-1$
		
		int index = binarySearch(arr,23);
		logger.info("main(String[]) -  binarySearch(int[])=" + index); //$NON-NLS-1$
	}
	
	public static void bubbleSort(int[] arr){
		for(int i=0;i<arr.length;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]>arr[j]){
					swap(arr, i, j);
				}
			}
		}
	}
	
	public static void selectSort(int[] arr){
		if(arr.length<=1)
			return ;
		int temp = -1;
		for(int i=0;i<arr.length;i++){
			temp = arr[i];
			int index = -1;
			for(int j=i+1;j<arr.length;j++){
				if(arr[j]<temp){
					temp = arr[j];
					index = j;
				}
			}
			if(index!=-1){
				swap(arr,i,index);
			}
		}
	}
	
	private static void insertSort(int[] arr){
		for(int i=1;i<arr.length;i++){
			int temp = arr[i];
			for(int j=i-1;j>=0;j--){
				if(arr[j]>temp){
					swap(arr, j, j+1);
				}
			}
		}
	}
	
	public static void quickSort(int[] arr,int low,int high){
		if(low>=high){
			return ;
		}
		int pivot = getPivot(arr,low,high);
		
		quickSort(arr, low, pivot-1);
		quickSort(arr, pivot+1, high);
	}
	
	private static int getPivot(int arr[],int low,int high){
		int temp = arr[low];
		while(low!=high){
			while(arr[high]>temp&&low<high){
				high--;
			}
			if(low<high)
				arr[low++] = arr[high];
			
			while(arr[low]<temp&&low<high){
				low++;
			}
			if(low<high)
				arr[high--] = arr[low];
			
			System.out.println(Arrays.toString(arr));
		}
		arr[low] = temp;
		return low;
	}
	
	private static void swap(int[] arr,int i,int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static int binarySearch(int[] arr,int value){
		int low = 0, high = arr.length;
        int middle = (high + low) / 2;
        int index = -1;
        while(low<high){
        	if(arr[middle]==value){
        		index = middle;
        		break;
        	}
        	
        	if(arr[middle]>value){
        		high = middle -1;
        	}else{
        		low = middle +1;
        	}
        	middle = (low + high) / 2;
        }
		return index;
	}
}
