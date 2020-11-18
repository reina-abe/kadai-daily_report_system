package models.validators;
//日報のタイトルと内容について必須入力チェックを行っている

import java.util.ArrayList;
import java.util.List;

import models.Report;

public class ReportValidator {
    public static List<String> validate(Report r) { //rを変数にしてvalidateメソッド
        List<String> errors = new ArrayList<String>(); //インスタンスerrors

        String title_error = _validateTitle(r.getTitle());
        //title_errorに_validateTitleメソッドで取得したタイトルを代入
        if(!title_error.equals("")) { //title_errorにデータが入ってたら
            errors.add(title_error); //errorsに追加
        }

        String content_error = _validateContent(r.getContent());
        //content_errorに_validateContentで取得した日報内容を代入

        if(!content_error.equals("")) { //content_errorにデータが入ってたら
            errors.add(content_error); ////errorsに追加
        }

        return errors; //エラーを返す
    }

    private static String _validateTitle(String title) { //_validateTitleメソッド
        if(title == null || title.equals("")) { //空だったら
            return "タイトルを入力してください。";
            }

        return ""; //
    }

    private static String _validateContent(String content) { //_validateContentメソッド
        if(content == null || content.equals("")) { //空だったら
            return "内容を入力してください。";
            }

        return "";
    }
}
