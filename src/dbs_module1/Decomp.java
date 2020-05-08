/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs_module1;
import java.lang.*;
import java.util.*;
/**
 *
 * @author StylishDheeru
 */
public class Decomp {
    static String Norm = "";
    static String C__KEY = "";
    static String dec = "";
    public static void init(int visited[]){
		for(int i=0;i<26;i++){
			visited[i] = 0;
		}
    }

    public static String sortString(String inputString) { 
        // convert input string to char array 
        char tempArray[] = inputString.toCharArray(); 
          
        // sort tempArray 
        Arrays.sort(tempArray); 
          
        // return new sorted string 
        return new String(tempArray); 
    }
    String[] total_array;
    Vector<String>left = new Vector<String>();    // vector storing leftside of each FD
     Vector<String>right = new Vector<String>();   // vector storing rightside of each FD
     
    
    
    public static Vector<String> printSubsets(char set[]){ 
    	Vector<String> answer = new Vector<String>();
        int n = set.length; 
  
        // Run a loop for printing all 2^n 
        // subsets one by one 
        for (int i = 0; i < (1<<n); i++) 
        { 
        	String sub = "";
             
  
            // Print current subset 
            for (int j = 0; j < n; j++){ 
                if ((i & (1 << j)) > 0){ 
                    sub = sub+set[j];
                } 
  			}
  			answer.add(sub);
    	}
    	return answer;
    }
    public static void deco(Vector<String> left,Vector<String> right,String[] total_array) {
        
		String total_attr="";
                
		for(int i=0;i<total_array.length;i++){
			total_attr = total_attr + total_array[i];
		}
		int arr[] = new int[26];

		for(int i=0;i<26;i++){
			arr[i] = -1; 						// initializing whole array to -1 (this is just an array to indicate essential attr)
		}

		int arr1[] = new int[26];

		for(String a : total_array){
			char ch = a.charAt(0);
			arr[ch-97] = 0;     				// initializing all present attributes to 0
		}

		for(int i=0;i<26;i++){
			arr1[i] = arr[i];   				 // duplicating array
		}

		

//		for(int i=0;i<4;i++){
//			String temp = scan.nextLine();							// Scanning the FDs
//			StringTokenizer str = new StringTokenizer(temp," ");    // Using StringTokenizer to split LHS and RHS by " ".
//			String temp_left = sortString(str.nextToken());
//			String temp_right = sortString(str.nextToken());
//			if(temp_left.equals(temp_right)==false){
//				left.add(temp_left);    							
//				right.add(temp_right);
//			}								
//		}

		for(int k=0;k<right.size();k++){
			String temp = (right.get(k)).toString();	// taking RHS of each FD
			for(int j=0;j<temp.length();j++){		
				char ch = temp.charAt(j);				// Taking each character of RHS
				arr1[ch-97] = 1;						// editing temp array to find essential attributes
			}		
		}

		String essential = "";							// empty string for essential attributes
		for(int i=0;i<26;i++){
			if(arr1[i]==0){
				char ch = (char)('a' + i);				// appending character to essential attribute string.
				essential = essential + ch;
			}
		}
		String non_ess = "";						// Initializing empty String for non-essential attributes.
		for(int i=0;i<26;i++){
			if(arr1[i]==1){							// checking condition for non-essential attributes.
				char ch = (char)('a'+i);
				non_ess = non_ess + ch;				// appending character to non-ess string.
			}
		}

		System.out.println("essential : "+essential);
		System.out.println("Non-Essential: "+non_ess);

		// int length_non_ess = non_ess.length();											// From this point
		// int temp = 1;
		// int temp1;																		//
		// String temp_arr[] = new String[(length_non_ess*(length_non_ess+1)/2)+1];
		// temp_arr[0] = essential;
		// for(int i=0;i<length_non_ess;i++){												//
		// 	for(int j=i;j<length_non_ess;j++){											//
		// 		temp_arr[temp] = non_ess.substring(i,j+1);								//
		// 		temp++;																	//
		// 	}																			//
		// }			
		// 																// Copied from internet(to find all subsets)
		// for(int i=1;i<temp;i++){
		// 	temp_arr[i] = temp_arr[i] + essential;										// adding essential attributes to the sets.
		// }
		// temp_Arr contains all sets

		char[] non_ess_array = non_ess.toCharArray();
		
		Vector<String>temp_arr = new Vector<String>();

		temp_arr = printSubsets(non_ess_array);

		for(int i=0;i<temp_arr.size();i++){
			String s0 = (temp_arr.get(i)).toString();
			s0 = s0 + essential;
			temp_arr.set(i,s0);
		}

		for(int i=0;i<temp_arr.size();i++){
			String temp_temp = temp_arr.get(i);
			String temp_final = sortString(temp_temp);
			temp_arr.set(i,temp_final);
		}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* WHAT CONTAINS WHAT:
	---	------------------------------------------------------------------------------------------------------------------------
	|	1) total_attr string : contains all attributes
	|	2) essential string : contains essential attributes
	|	3) non-ess string : contains non-essential attributes
	|	4) int arr[] : contains -1 or 0 indicating which attributes are present(0) out of 26 alphabets
	|	5) int arr1[] : contains -1 , 0 ,1 indicating non-attributes , non-essential attributes and essential attributes respectively
	|	6) temp_arr[] : array of strings. Contains all the sets of combinations of non-essential attributes and essential attributes like abc,abd,abe,abcd,abce,abde etc (here ab are essential attributes)
	|	7) Vector left : contains left side of functional dependencies (Stored as Strings).
	|	8) Vector right : contains right side of functional dependencies (Stored as Strings).
		9) Vector skey : contains all skeys
		10) Vector ckey : contains all ckeys
		11) int max_normal[] : max normal forms of all fds
	-----------------------------------------------------------------------------------------------------------------------------

		*/
	///////////////////////////////////////// Finding prime attributes and ckeys /////////////////////////////////////////////////////
		int visited[] = new int[26];
		visited[0] = 1;
		int check[] = new int[26];
		Vector<String>skey = new Vector<String>();
		Vector<String>ckey = new Vector<String>();
		for(int i=0;i<temp_arr.size();i++){
			//System.out.println("Checking "+temp_arr.get(i));
			obj1 obj = new obj1((temp_arr.get(i)).toString(),visited,check,left,right);
			if(obj.global_flag==0){
				skey.add((temp_arr.get(i)).toString());
			}
		}
	//	System.out.println("\n");
	//	System.out.println("\n");

		for(int i=0;i<skey.size();i++){
			String skey_temp = skey.get(i);
			String skey_final = sortString(skey_temp);
			skey.set(i,skey_final);
		}
		
		int visited1[] = new int[26];
		int visited2[] = new int[26];
		int removed[] = new int[skey.size()];
		for(int i=0;i<skey.size();i++){
			removed[i] = 0;
		}
		

		for(int i=0;i<skey.size();i++){
			if(removed[i]==0){
				String s1 = (skey.get(i)).toString();
				for(int j=0;j<skey.size();j++){
					if(i!=j){
						int ckey_flag = 0;
						String s2 = (skey.get(j)).toString();
						for(int k=0;k<s1.length();k++){
							String used = "";
							used = used + s1.charAt(k);
							if(s2.contains(used)==false){
								ckey_flag = 1;
								break;
							}
						}
						if(ckey_flag==0){
							removed[j] = 1;
						}
					}
				}
			}
		}
	

		for(int i=0;i<skey.size();i++){
			if(removed[i]==0){
				ckey.add(skey.get(i));
			}
		}
		// System.out.println("\n");
		// System.out.println("\n");
		
		for(int i=0;i<ckey.size();i++){
			String ckey_temp = ckey.get(i);
			String ckey_final = sortString(ckey_temp);
			ckey.set(i,ckey_final);
		}
		System.out.println("SKEY:");
		for(int i=0;i<skey.size();i++){
			System.out.println(skey.get(i));
		}
		System.out.println("CKEY");
		for(int i=0;i<ckey.size();i++){
			System.out.println(ckey.get(i));
                                C__KEY = C__KEY + ckey.get(i);
                        
		}
		int prime_attributes[] = new int[26];
		init(prime_attributes);
		for(int i=0;i<ckey.size();i++){
			String ck = (ckey.get(i)).toString();
			for(int j=0;j<ck.length();j++){
				prime_attributes[(int)(ck.charAt(j)-'a')] = 1;
			}
		}
	
		// At this point, we have all super keys, candidate keys and prime attributes and non-prime attributes.

		//////////////////////////////////////////////////////// Finding Normal Forms /////////////////////////////////////////////////

		int max_normal[] = new int[left.size()];
		for(int i=0;i<left.size();i++){
			max_normal[i] = 1;
		}

		for(int i=0;i<left.size();i++){
			String left_fd = (left.get(i)).toString();
			String right_fd = (right.get(i)).toString();

		//	System.out.println("Checking for "+left_fd+" and "+right_fd);

			Normal obj2 = new Normal(left_fd,right_fd,ckey,skey,prime_attributes);
			if(obj2.check_2nf(left_fd,right_fd,ckey,prime_attributes)==1){
			//	System.out.println("Setting 2");
				max_normal[i]  = 2;
			}
			if(obj2.check_3nf(left_fd,right_fd,skey,prime_attributes)==1){
			//	System.out.println("Setting 3");
				max_normal[i] = 3;
			}
			if(obj2.check_bcnf(left_fd,right_fd,skey)==1){
			//	System.out.println("Setting 4");
				max_normal[i] = 4;
			}
		}
		System.out.println(" ");
		for(int i=0;i<left.size();i++){
			System.out.print(max_normal[i]+" ");
		}
		// Now we have max_normal forms of all fds in max_normal array

		
		int normal_form = max_normal[0];
		for(int i=1;i<left.size();i++){
			if(max_normal[i]<normal_form){
				normal_form = max_normal[i];
			}
		}
		if(normal_form==4){
			System.out.println("The Normal Form: BCNF");
                        Norm = Norm + "BCNF";
                        
		}
		else{
			System.out.println("The Normal Form: "+normal_form);
                        Norm = Norm + normal_form;
		}

		/////////////////////////////////////////////////////Decomposition into x+1////////////////////////////////////////////////////////////////
		if(normal_form<4){
			String duplicate_attr  = new String();                 // duplicate string containing all attributes.
			duplicate_attr = total_attr;
			Vector<String> Decomposition = new Vector<String>();
			Vector<String>Keys = new Vector<String>();
			for(int i=0;i<left.size();i++){
				if(max_normal[i]==normal_form){
					String lt_temp = (left.get(i)).toString();
					String rt_temp = (right.get(i)).toString();
					for(int j=0;j<rt_temp.length();j++){
						duplicate_attr = duplicate_attr.replace(Character.toString(rt_temp.charAt(j)),"");
					}
					String total_temp = lt_temp + rt_temp;
					Decomposition.add(total_temp);
					Keys.add(lt_temp);
				}
			}
			System.out.println("Decomposition:");
			System.out.println(duplicate_attr);

			for(int i=0;i<ckey.size();i++){
				if(duplicate_attr.contains(ckey.get(i))==true){
					System.out.println("Key: "+ckey.get(i));
//                                        System.out.print("(");
//                                        for(int z =0;z<ckey.get(z).length();z++){
//                                            System.out.print(ckey.get(i).charAt(z)+",");
//                                        }
//                                        System.out.print(")");
//                                        System.out.println();
					break;
				}
			}


			for(int i=0;i<Decomposition.size();i++){
				System.out.println(Decomposition.get(i));
                                dec = dec +"("+ Decomposition.get(i) +")"+ "   Key :" + Keys.get(i) +"\n";
				System.out.println("Key : "+Keys.get(i));
			}
		}  
    }
    
}
