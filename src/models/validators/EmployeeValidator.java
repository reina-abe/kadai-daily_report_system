package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

//入力値チェック

public class EmployeeValidator {

    //validateメソッド
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {

        List<String> errors = new ArrayList<String>(); //インスタンス"errors"
        //社員番号、氏名、パスワードにエラーがあればerrorsに追加
        String code_error = _validateCode(e.getCode(), code_duplicate_check_flag);
        //_validateCodeメソッドでコードを取得、重複チェックしたらcode_errorに代入
        if(!code_error.equals("")) { //code_errorが空じゃない＝エラーがあるなら
            errors.add(code_error); //errorsにcode_errorのデータを保存
        }

        String name_error = _validateName(e.getName());
        if(!name_error.equals("")){
            errors.add(name_error);
        }

        String password_error = _validatePassword(e.getPassword(),password_check_flag);
        if(!password_error.equals("")){
            errors.add(password_error);
        }

        return errors; //エラーを返す
    }

    //社員番号（_validateCodeメソッド）
    public static String _validateCode(String code, Boolean code_duplicate_check_flag) {
    //String code_error = _validateCode(e.getCode(), code_duplicate_check_flag)にの引数の値を当てはめる
        // 必須入力チェック
        if(code == null || code.equals("")){
                return "社員番号を入力してください。";
    }      //社員番号が空だったら"code_error"にエラーを保存

        // すでに登録されている社員番号との重複チェック
        if(code_duplicate_check_flag){
        //code_duplicate_check_flag == trueである時
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                           .setParameter("code", code).getSingleResult(); //この行はクエリの条件
            //checkRegisteredCodeのNamedQueryで渡された社員番号のcodeがテーブル内にあるかの件数を取得

            em.close();

            if(employees_count > 0) {
                return "入力された社員番号の情報はすでに存在しています。";
            }
        }

        return "";

        }

     // 社員名の必須入力チェック（_validateNameメソッド）
        private static String _validateName(String name) {
            if(name == null || name.equals("")) {
                return "氏名を入力してください。";
            }

            return "";
        }

     // パスワードの必須入力チェック
        private static String _validatePassword(String password, Boolean password_check_flag) {
            // パスワードを変更する場合のみ実行
            if(password_check_flag && (password == null || password.equals(""))) {
                return "パスワードを入力してください。";
            }
            return "";
        }
}
