import java.util.*;
import java.io.*;
public class Main {
    
    public static void main(String[] args) throws Exception {
    	String[] arr = new String[] {"A","B","C","D","E","F"};
    	for(String sx : arr) {
    	Scanner sc = new Scanner(new FileReader(sx+".in.txt"));
    	PrintWriter pw = new PrintWriter( new File(sx+".out.txt"));
    	 int number_of_projects=0;
         StringBuilder ans=new StringBuilder();
        int c=sc.nextInt();
        int p=sc.nextInt();
        Contributer[]con=new Contributer[c];
        for(int i=0;i<c;i++){
            String name=sc.next();
            con[i]=new Contributer(name);
            int n=sc.nextInt();
            for(int j=0;j<n;j++){
                String skilName=sc.next();
                int v=sc.nextInt();
                con[i].addSkill(skilName,v);
            }
        }
        ArrayList<project> pro = new ArrayList<>();
       // project[]pro=new project[p];
        for(int i=0;i<p;i++){
            String name=sc.next();
            int d=sc.nextInt();
            int s=sc.nextInt();
            int b=sc.nextInt();
            int r=sc.nextInt();
            String[]na=new String[r];
            int[]values=new int[r];
            for(int j=0;j<r;j++){
                na[j]=sc.next();
                values[j]=sc.nextInt();
            }
            pro.add(new project(name,d,s,b,r,na,values));
        }

     //   Arrays.sort(pro);
        Collections.shuffle(pro);
        Collections.sort(pro);
        for(project x : pro) {
        	Contributer[] cons = x.Do(con);
        	if(cons!=null) {
        		number_of_projects ++;
        		ans.append(x.name+"\n");
        		for(Contributer y: cons) ans.append(y.name+" ");
        		ans.append("\n");
        	}
        }	
        
        pw.println(number_of_projects);
        pw.println(ans);
  
        pw.close();}
    }

    static class project implements Comparable<project> {
        String name;
        int days;
        int score;
        int deadline;
        int num_roles;
        String[]skills;
        int[]levels;
        public project(String name,int days,int score,int deadline,int num_roles,String[]skills,int[]levels) {
            this.name = name;
        	this.days=days;
            this.score=score;
            this.deadline=deadline;
            this.num_roles=num_roles;
            this.skills=skills.clone();
            this.levels=levels.clone();
        }
        public Contributer[] Do(Contributer[]a){
            int n=a.length;
            int max=0;
            boolean[]busy=new boolean[n];
            Contributer[]ret=new Contributer[num_roles];
            for(int i=0;i<num_roles;i++){
                int ind=-1;
                for(int j=0;j<n;j++){
                    if(!busy[j]&&a[j].canDoRole(skills[i],levels[i] )!=null && a[j].canDoRole(skills[i],levels[i] )){
                        if(ind==-1){
                            ind=j;
                        }else if(a[j].busy_until<a[ind].busy_until){
                            ind=j;
                        }
                    }
                    
                }
                if(ind==-1) return null;
                busy[ind] = true;
                ret[i]=a[ind];
                max=Math.max(max,a[ind].busy_until);
               // return null;
            }
            if(0>=deadline+score-max-days){
                return null;
            }
            for(int i=0;i<num_roles;i++){
                ret[i].busy_until=max+days;
                ret[i].doesRole(skills[i],levels[i]);
            }
//            number_of_projects++;
            return ret;
        }
		@Override
		public int compareTo(project o) {
			// TODO Auto-generated method stub
			return deadline+days+num_roles-o.deadline-o.days-num_roles;
		}
		
		public double get() {
			return ( 1.0 *deadline*num_roles*days)/((1.0+score)*(1.0*score)*(1.0*score));
		}

    }

    static public class Contributer {
        String name;
        HashMap<String, Integer> skills;
        int busy_until;
        public Contributer(String name){
            this.name = name;
            skills = new HashMap<>();
            busy_until=0;
        }

        public void addSkill(String skillName, int skillLevel) {
            skills.put(skillName, skillLevel);
        }

        public void doesRole(String roleSkill, int skillLevel){
            int skill = skills.getOrDefault(roleSkill, 0);
            if(skill <= skillLevel)
                skills.put(roleSkill, skill + 1);
        }

        public Boolean canDoRole(String roleSkill, int skillLevel){
            int currSkillLevel = skills.getOrDefault(roleSkill, 0);
            if(currSkillLevel >= skillLevel)
                return true;
            if(currSkillLevel == skillLevel - 1)
                return null;
            return false;
        }
    }









    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public long[] nextlongArray(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }

        public Long[] nextLongArray(int n) throws IOException {
            Long[] a = new Long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }

        public int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public boolean ready() throws IOException {
            return br.ready();
        }

    }



    static Scanner sc = new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(System.out);
}