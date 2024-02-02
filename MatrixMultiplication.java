import java.util.Scanner;

class Multiplication implements Runnable{
    int [][] matA ;
    int [][] matB;
    int [][] matC;
    int max_threads,i;
    Multiplication(int [][] matA,int [][] matB, int [][] matC, int max ,int i){
        this.matA = matA;
        this.matB = matB;
        this.matC = matC;
        max_threads = max;
        this.i =i;
    }
    public void run(){
        int c = matA[0].length;
        for(int j =0;j<c;j++){
            for(int k =0;k<c;k++){
                matC[i][j] += matA[i][k]*matB[k][j];
            }
        }
    }
    public int[][] input(int [][] matrix){
        Scanner sc = new Scanner(System.in);
        int n = matrix.length;
        int m = matrix[0].length;
        for(int i=0;i<n;i++){
            for(int j =0;j<m;j++){
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }
    public void output(){
        int n = matC.length;
        int m = matC[0].length;
        for(int i=0;i<n;i++){
            for(int j =0;j<m;j++){
                System.out.print(matC[i][j] + " ");
            }
            System.out.println();
        }
    }
}
public class MatrixMultiplication {
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        int r1,c1,r2,c2;
        System.out.println("Enter the no of rows and column of matrix A");
        r1 = sc.nextInt();
        c1 = sc.nextInt();
        int [][] matA = new int[r1][c1];
        System.out.println("Enter the no of rows and column of matrix B");
        r2 = sc.nextInt();
        c2 = sc.nextInt();
        int [][] matB = new int[r2][c2];
        int [][] matC = new int[r1][c2];
        Multiplication obj = new Multiplication(matA, matB, matC, r1, 0);
        System.out.println("Enter the elements of matrix A");
        matA = obj.input(matA);
        System.out.println("Enter the elements of matrix B");
        matB = obj.input(matB);
        Thread threads[] = new Thread[r1];
        for(int i =0;i<r1;i++){
            threads[i] = new Thread(new Multiplication(matA,matB,matC,r1,i));
            threads[i].start();
        }
        for(int i =0;i<r1;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("The output matrix is:");
        obj.output();
    }

}
