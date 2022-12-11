//package SERVER;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

public class ConnectServer {
    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    ConnectServer() { // 서버 연결 함수
        String url = "jdbc:mysql://localhost:3306/marketeam"; //서버
        String user = "root";           //유저명
        String password = "6482";       //비번


        try { //실행구간
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("연결 성공!");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            System.out.println("라이브러리 연결 확인필요");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }
    }//public ConnectServer

    public void DisconnectServer() { //서버 연결 해제
        if (this.conn != null) {
            try {
                this.conn.close();
                System.out.println("연결 끊기");
            } catch (SQLException e) {
            }
        }
    }//disconnectserver

    public boolean InputData(int ID, int pw) { //signup 데이터 삽입 함수 //완성
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO login (id, pw) VALUES (?,?)";
            //매개변수화된 SQL문 실행 메소드 pstmt
            pstmt = this.conn.prepareStatement(sql);
            //변수 삽입
            pstmt.setInt(1, ID);
            pstmt.setInt(2, pw);
            //데이터 저장 확인
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
            return false;
        } catch (SQLException e) {
            System.out.println("ERROR in inputdata");
            System.out.println("SQLException");
            e.printStackTrace();
            return true; //이미 있는 아이디입니다. 출력.
        }
    }//inputdata

    public boolean FindID(int id, int pw) { //login 데이터를 가져오는 함수 //완성
        try {
            String sql = "SELECT id, pw FROM login WHERE id=?";
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1, id); //입력한 id를 통해서 서버와 확인
            rs = pstmt.executeQuery();

            if (rs.next()) { //일치하는 id를 찾아도 pw가 일치하지 않으면 false 되야함.
                User user = new User();
                user.setID(rs.getInt("id"));
                //user.setPW(rs.getInt("pw"));
                System.out.println(user.toString());
                pstmt.close();
                return CheckPW(pw);
            } else {
                System.out.println("존재하지 하지 않음");
                pstmt.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("ERROR in FindID");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in FindID");
            e.printStackTrace();
        }
        return false;
    }//FindID

    public boolean CheckPW(int PW) { //비밀번호 확인 함수 //완성
        try {
            String sql = "SELECT id, pw FROM login WHERE pw=?";
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1, PW); //입력한 id를 통해서 서버와 확인
            rs = pstmt.executeQuery();

            if (rs.next()) { //일치하는 id를 찾아도 pw가 일치하지 않으면 false 되야함.
                User user = new User();
                user.setPW(rs.getInt("pw"));
                System.out.println(user.toString());
                pstmt.close();
                return true;
            } else {
                System.out.println("존재하지 하지 않음");
                pstmt.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("ERROR in CheckPW");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in CheckPW");
            e.printStackTrace();
        }
        return false;
    }//CheckPW

    public void CheckList(int userlist[], String major, String name) { //체크리스트 저장할 DB 접근 User_info 부분
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO user_info " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = this.conn.prepareStatement(sql);

            //user_info에서 받은 정보를 배열에 삽입하고, 일부는 직접 가져와야함.
            /* 1:id, 2:grade, 3:leader 4:announce 5:ppt 6:frontend 7:backend 8:selfability 9:teammate*/
            int i;
            for (i = 0; i < 9; i++) {
                pstmt.setInt(i + 1, userlist[i]);
            }

            pstmt.setString(10, name); //name
            pstmt.setString(11, major); //major
            int sum = 0;
            for (int k = 2; k < 8; k++) {
                if (k == 6)
                    continue;
                sum = sum + userlist[k];
            }
            pstmt.setInt(12, sum); //score
            pstmt.setInt(13, 0); //subject 게시글 유무
            pstmt.setInt(14, 0); //list 경험횟수
            pstmt.setDouble(15, 0.0); //group 평점


            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("ERROR in CheckList");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in CheckList");
            e.printStackTrace();
        }
    } //CheckList

    public void RewriteInfo(int ID, int var, int value){ //subject, list, group에 대한 업데이트 함수
        //arr내부에 변수 들의 수정값 들어있음 또는 따로 들어있음
        try{
            String sql = "UPDATE user_info";
            String tail = "WHERE id =" + ID; //고정되는 ID로 DB 접근.
            stmt = null;
            stmt = conn.createStatement();

            switch (var){
                case 1: //subject 값 변경
                    stmt.executeUpdate("UPDATE user_info SET subject = " + value + " " + tail);
                    break;
                case 2: //list 변경
                    stmt.executeUpdate("UPDATE user_info SET list = " + value + " " + tail);
                    break;
                case 3: //group 변경
                    stmt.executeUpdate("UPDATE user_info SET groupnum = " + value + " " + tail);
                    break;
                case 4: //group 변경
                    stmt.executeUpdate("UPDATE user_info SET score = " + value + " " + tail);
                    break;
                default:
                    break;
            }

        }catch(SQLException e) {
            System.out.println("ERROR in Rewriteinfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in Rewriteinfo");
            e.printStackTrace();
        }
    }//Rewriteuserinfo 정보수정함수

    public int GetUserinfo(int ID, int num) {
        try {
            String sql = "SELECT * FROM user_info WHERE id =" + ID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int ret = 0;
            switch (num) {
                case 1:
                    break;
                case 2: //grade 출력
                    if (rs.next()) {
                        ret = rs.getInt("grade");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                case 3: //leader
                    if (rs.next()) {
                        ret = rs.getInt("leader");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                case 4: //announce
                    if (rs.next()) {
                        ret = rs.getInt("announe");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                case 5: //ppt
                    if (rs.next()) {
                        ret = rs.getInt("ppt");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                case 6: //document
                    if (rs.next()) {
                        ret = rs.getInt("document");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                case 7: //front-back
                    if (rs.next()) {
                        ret = rs.getInt("front-back");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                case 8: //selfability
                    if (rs.next()) {
                        ret = rs.getInt("selfability");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                case 9: //teammate
                    if (rs.next()) {
                        ret = rs.getInt("teammate");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 10:
                    if (rs.next()) {
                        ret = rs.getInt("score");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 11: //teammate
                    if (rs.next()) {
                        ret = rs.getInt("subject");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 12: //teammate
                    if (rs.next()) {
                        ret = rs.getInt("list");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 13: //teammate
                    if (rs.next()) {
                        ret = rs.getInt("groupnum");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;

                default:
                    System.out.println("존재하지 하지 않음");
                    break;
            }
            rs.close();
            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;
    }//int GetUserinfo

    public int matching(int i)
    {
        try {
            String sql = "SELECT id, score FROM marketeam.user_info where subject =" + i + " AND list = 0";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            LinkedList<matching_user> userVector = new LinkedList<matching_user>();
            while(rs.next())
            {
                matching_user user = new matching_user();
                user.inputdata(rs.getInt("id"), rs.getInt("score"));
                userVector.add(user);
            }

            int n = GetCourse(1,3);
            int matching_num = userVector.size();


            Collections.sort(userVector, (o1, o2) -> o2.score - o1.score);
            int teamnum = 1;
            while(true)
            {
                if(teamnum > matching_num/n) break;

                int num = userVector.getFirst().hakbun;
                RewriteInfo(num,3,teamnum);
                userVector.removeFirst();

                int num2 =userVector.getLast().hakbun;
                RewriteInfo(num2,3,teamnum);
                userVector.removeLast();

                teamnum++;
            }

            int teamnum2 = 1;

            matching_num = userVector.size();
            teamnum = 1;

            if(n -2 > 1)
            {
                while(true)
                {
                    if(teamnum > matching_num/n) break;

                    RewriteInfo(userVector.getFirst().hakbun,3,teamnum);
                    userVector.removeFirst();

                    RewriteInfo(userVector.getLast().hakbun,3,teamnum);
                    userVector.removeLast();

                    teamnum++;
                }
            }

            while(true)
            {
                if(userVector.isEmpty()) break;

                RewriteInfo(userVector.getLast().hakbun,3,teamnum2);
                userVector.removeLast();
                teamnum2++;
            }



            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;
    }

    public String GetUserinfo(int ID, String str) {
        try {
            String sql = "SELECT * FROM user_info WHERE id =" + ID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            switch (str) {
                case "grade":
                    break;
                case "major":
                    if (rs.next()) {
                        String major = rs.getString("major");
                        System.out.println(major);
                        rs.close();
                        stmt.close();
                        return major;
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case "name":
                    if (rs.next()) {
                        String name = rs.getString("name");
                        System.out.println(name);
                        rs.close();
                        stmt.close();
                        return name;
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음");
                    break;
            }
            rs.close();
            stmt.close();
            return "Failed";

        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return "Failed";
    }//String GetUserinfo

    public void SetCourse(String [] str, int subnum, int teamnum){ //과목 게시글 생성 페이지(교수만 접근?)
        //교수는 과목을 여러개 맡을 수 있음, 과목이 primary 값을 가짐.
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO subject () VALUES (?, ?, ?, ?, ?, ?, ?, ?)";  //8개
            pstmt = this.conn.prepareStatement(sql);

            pstmt.setInt(1, 0); //인덱스 수 건들지 말기
            for(int i = 1; i < 5; i++)
            {
                pstmt.setString(i+1,str[i-1]);
            }
            pstmt.setInt(6, Integer.parseInt(str[4])); //학년
            pstmt.setInt(7, subnum); //subnum
            pstmt.setInt(8,teamnum); //teamnum

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in SetCourse");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in SetCourse");
            e.printStackTrace();
        }
    }//SetCourse

    public String GetCourse(int i, String str) {
        try {
            String sql = "SELECT * FROM subject WHERE num =" + i;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            String ret;
            switch (str) {
                case "subject":
                    if (rs.next()) {
                        ret = rs.getString("subject");
                        System.out.println(ret);
                        rs.close();
                        stmt.close();
                        return ret;
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case "name":
                    if (rs.next()) {
                        ret = rs.getString("prof_name");
                        System.out.println(ret);
                        rs.close();
                        stmt.close();
                        return ret;
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case "language":
                    if (rs.next()) {
                        ret = rs.getString("language");
                        System.out.println(ret);
                        rs.close();
                        stmt.close();
                        return ret;
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case "precourse":
                    if (rs.next()) {
                        ret = rs.getString("pre-course");
                        System.out.println(ret);
                        rs.close();
                        stmt.close();
                        return ret;
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음");
                    break;
            }
            rs.close();
            stmt.close();
            return "Failed";
        } catch (SQLException e) {
            System.out.println("ERROR in GetCourse");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetCourse");
            e.printStackTrace();
        }
        return "Failed";
    }//String GetCourse

    public int GetCourse(int i, int num) {
        try {
            String sql = "SELECT * FROM subject WHERE num =" + i;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int ret = 0;
            switch (num) {
                case 1: //grade
                    if (rs.next()) {
                        ret = rs.getInt("grade");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 2: //maximum
                    if (rs.next()) {
                        ret = rs.getInt("maximum");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 3: //teamnum
                    if (rs.next()) {
                        ret = rs.getInt("teamnum");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음");
                    break;
            }
            rs.close();
            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;
    }//int GetCourse

    public int subject_row_size() {
        try {
            String sql = "SELECT * FROM subject";
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            rs.close();
            stmt.close();
            return size;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;

    }
    // Title
    public int SetTitle(int ID, String title, String content,int x){
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO title () VALUES (?,?,?,?,?)"; //5개
            pstmt = this.conn.prepareStatement(sql);
            String sql2 = "SELECT * FROM title";
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql2);
            rs.last();
            int size = rs.getRow() + 1;
            rs.beforeFirst();
            pstmt.setInt(1,size);
            pstmt.setInt(2, ID);
            pstmt.setString(3, title); //
            pstmt.setString(4, content); //최대 200자
            pstmt.setInt(5,x);

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
            rs.close();
            stmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in SetTitle");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in SetTitle");
            e.printStackTrace();
        }
        return 0;
    }

    public String GetTitle(int subnum, int num, String name){
        try{
            String sql = "SELECT * FROM title WHERE subnum =" + subnum  + " AND num =" + num;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            String ret = "Failed";
            switch(name) {
                case "id" :
                    if (rs.next()) {
                        int a;
                        ret = rs.getString("id");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case "title" :
                    if (rs.next()) {
                        ret = rs.getString("title");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case "content" :
                    if (rs.next()) {
                        ret = rs.getString("content");
                        System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return ret;
        }catch(SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return "Failed";
    }

    public int SetTeam(int Subnum, int arr[]){
        try {
            String sql = "INSERT INTO team () VALUES (?, ?, ?, ?, ?, ? ,? ,?)"; //8개
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1,Subnum);
            for(int i = 0; i < 7; i++)
                pstmt.setInt(i+2, arr[i]);

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in SetTeam");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in SetTeam");
            e.printStackTrace();
        }
        return 0;
    }

    public int getTeam(int Subnum, int Teamnum , int num){
        try{
            String sql = "SELECT * FROM team WHERE subnum =" + Subnum + " AND teamnum =" + Teamnum;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int ret = 0;
            switch(num) {
                case 1: //subnum
                    if (rs.next()) {
                        ret = rs.getInt(1); //subnum
                        //System.out.println(ret);
                    }
                    break;
                case 2: //teamnum
                    if (rs.next()) {
                        ret = rs.getInt(2); //teamnum
                        //System.out.println(ret);
                    }
                    break;
                case 3: //Leader
                    if (rs.next()) {
                        ret = rs.getInt(3); //Leader
                        //System.out.println(ret);
                    }
                    break;
                case 4: //member1
                    if (rs.next()) {
                        ret = rs.getInt(4); //member1
                        //System.out.println(ret);
                    }
                    break;
                case 5: //member2
                    if (rs.next()) {
                        ret = rs.getInt(5); //member2
                        //System.out.println(ret);
                    }
                    break;
                case 6: //member3
                    if (rs.next()) {
                        ret = rs.getInt(6); //member3
                        //System.out.println(ret);
                    }
                    break;
                case 7: //member4
                    if (rs.next()) {
                        ret = rs.getInt(7); //member4
                        //System.out.println(ret);
                    }
                    break;
                case 8: //current num
                    if (rs.next()) {
                        ret = rs.getInt(8); //current num
                        //System.out.println(ret);
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return ret;
        }catch(SQLException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }
        return 0;
    }

    public int getTeam_subteam(int id, int num, int i){
        try{
            String sql = "SELECT subnum, teamnum FROM team WHERE Leader =" + id + " OR member_1 =" + id + " OR member_2 =" + id + " OR member_3 =" + id + " OR member_4 = " + id;
            stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            int ret = 0;
            switch(num) {
                case 1: //subnum
                    if(rs.absolute(i))
                    {
                        ret = rs.getInt(1);
                    }
                    break;
                case 2: //teamnum
                    if(rs.absolute(i))
                    {
                        ret = rs.getInt(2);
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return ret;
        }catch(SQLException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }
        return 0;
    }

    public int teamcount(int subnum) {
        try {
            String sql = "SELECT teamnum FROM team WHERE subnum =" + subnum;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            rs.close();
            stmt.close();
            return size;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;

    }

    public int teamcount_id(int id) {
        try {
            String sql = "SELECT * FROM team WHERE Leader =" + id + " OR member_1 =" + id + " OR member_2 =" + id + " OR member_3 =" + id + " OR member_4 = " + id;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            rs.close();
            stmt.close();
            return size;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;

    }

    public int teamtitlecount(int subnum) {
        try {
            String sql = "SELECT * FROM title WHERE subnum = "+ subnum;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            rs.close();
            stmt.close();
            return size;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;

    }

    public int deleteteam(int Subnum, int Teamnum , int Leader_id){
        try{
            String sql = "SELECT * FROM team WHERE subnum =" + Subnum + " AND current_num !=" + Teamnum;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int i = 1;
            while (rs.next())
            {
                if(i > 4) break;
                RewriteInfo(Leader_id,2,0);
                int id = rs.getInt("member_"+ i);
                System.out.println(id);
                if(id == 0) break;
                RewriteInfo(id,2,0);
                i++;
            }

            String sql2 = "DELETE FROM team WHERE Leader =" + Leader_id;
            stmt.executeUpdate(sql2);
            String sql3 = "DELETE FROM title WHERE id =" + Leader_id;
            stmt.executeUpdate(sql3);

            rs.close();
            stmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }
        return 0;
    }

    public int SetRequest(int arr[]){ //[0] = num, [1] = subnum, [2] = titlenum, [3] = id
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO request () VALUES (?, ?, ?, ?)"; //4개
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1,requestcount()+1);
            for(int i=0;i<3;i++) {
                pstmt.setInt(i + 2, arr[i]);
            }

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("ERROR in SetTitle");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in SetTitle");
            e.printStackTrace();
        }
        return 0;
    }

    public int GetRequest(int leader_id, int num, int i){
        try{
            String sql = "SELECT subnum, id FROM request WHERE leader =" + leader_id;
            stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            int ret = 0;
            switch(num) {
                case 1: //subnum
                    if(rs.absolute(i))
                    {
                        ret = rs.getInt(1);
                    }
                    break;
                case 2: //id
                    if(rs.absolute(i))
                    {
                        ret = rs.getInt(2);
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return ret;
        }catch(SQLException e) {
            System.out.println("ERROR in GetRequest");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetRequest");
            e.printStackTrace();
        }
        return 0;
    }

    public int requestcount(int leader_id) {
        try {
            String sql = "SELECT * FROM request WHERE leader =" + leader_id;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            rs.close();
            stmt.close();
            return size;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;

    }

    public int requestcount() {
        try {
            String sql = "SELECT * FROM request";
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            rs.close();
            stmt.close();
            return size;
        } catch (SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;

    }

    public void Rewriteteam(int subnum,int teamnum, int var, int value){ //subject, list, group에 대한 업데이트 함수
        //arr내부에 변수 들의 수정값 들어있음 또는 따로 들어있음
        try{
            String sql = "UPDATE team";
            String tail = "WHERE subnum =" + subnum + " AND teamnum =" + teamnum; //고정되는 ID로 DB 접근.
            stmt = null;
            stmt = conn.createStatement();

            switch (var){
                case 1:
                    stmt.executeUpdate("UPDATE team SET member_1 = " + value + " " + tail);
                    break;
                case 2:
                    stmt.executeUpdate("UPDATE team SET member_2 = " + value + " " + tail);
                    break;
                case 3:
                    stmt.executeUpdate("UPDATE team SET member_3 = " + value + " " + tail);
                    break;
                case 4:
                    stmt.executeUpdate("UPDATE team SET member_4 = " + value + " " + tail);
                    break;
                case 5:
                    stmt.executeUpdate("UPDATE team SET current_num = current_num +" + value + " " + tail);
                default:
                    break;
            }

        }catch(SQLException e) {
            System.out.println("ERROR in Rewriteinfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in Rewriteinfo");
            e.printStackTrace();
        }
    }//Rewriteuserinfo 정보수정함수

    public int deleterequest(int id){
        try{
            String sql = "SELECT * FROM request";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            String sql2 = "DELETE FROM request WHERE id =" + id;
            stmt.executeUpdate(sql2);

            rs.close();
            stmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetTeam");
            e.printStackTrace();
        }
        return 0;
    }

}
