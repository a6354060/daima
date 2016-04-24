public class Match{
     static  String p="ksjsfgdf";
	 static String t="jskksjsgdffjjdslfjdsfsfsdfsdf fds";
     static int[] next;
	public Match(){
	    int len=p.length();  // 得到p串的长度
	    next=new int[len];   //实例化数组
	 }
	 // 得到模式p的next数组 next[i] 表示 当pi与tr匹配出现不相等的时候 就从p(i-1) 于tr进行比较
	  public  int[] getNext(){
	       next[0]=-1;
		  int i=0,j=-1;      
	   // 得到p中每一个字符的next值
	   while(i<p.length()-1){
	    
	    if(j==-1||p.charAt(i)==p.charAt(j)){
		    ++i; // 匹配下一个
			++j; // 最长真前缀等于最长真后缀的值加一
			next[i]=j; // next[i+1]的值
		}else{
		   j=next[j]; // 比较pi 于p(j-1)是否相等 重复前面做的事 直到最后next[i]=-1为止表示已经没有匹配的了 	  
		}  
	   }
              return next;
	  }

   // 与目标串进行匹配
  
     public  int kmp(String t,String p,int[] next){
	      int i=0,j=0;
        
            while(i<t.length()&&j<p.length()){
		        
				if(j==-1||t.charAt(i)==p.charAt(j)){ 
				   i++;
				   j++;
				}else{
				   j=next[j]; // 如果某个字符与目标串一个都没匹配返回-1 然后从目标的下一个进行匹配
				}	     
			}      
        if(j==p.length()){
		   return (i-p.length());
		}else{
		  return -1;
		}	 	 
	 }
  // 传统的方法
	public int BF(String s,String t){
	     int index=0;
		 int j=0,i=0;
	   while(i<s.length()&&j<t.length()){
	        if(s.charAt(i)==t.charAt(j)){
			     i++;j++;
			}else{
			   index++; i=index;j=0;	 
		    }
	   }
	   if(j==t.length()){
	     return index;
	   }else{
	     return -1;
	   }

	 }
  // 主函数
  public static void main(String args[]){
    Match match= new Match(); 
   long startTime=System.nanoTime();
	int[] n= match.getNext();
	int f=match.kmp(t,p,n);
	long endTime =System.nanoTime();
    System.out.println("位置是："+f+"----------KMP共花了"+(endTime-startTime)+"纳秒");
    
	 int a= match.BF(t,p);  
	 System.out.println("位置是："+a+"----------BF共花了"+(System.nanoTime()-endTime)+"纳秒");	
  
  }
    



}