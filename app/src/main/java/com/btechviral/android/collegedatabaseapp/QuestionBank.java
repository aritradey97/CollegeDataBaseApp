package com.btechviral.android.collegedatabaseapp;

public class QuestionBank {
    private String questions[] = {
            "Which of these is a programming language ?",
            "What is the built in library function to adjust the allocated dynamic memory size.",
            "The default executable generation on UNIX for a C program is ",
            "Where to place “f” with a double constant 3.14 to specify it as a float?",
            "Which library function can convert an integer/long to a string?"
    };
    private String options[][] = {
            {"Esolang","Lisp","CSS","Aba"},
            {"malloc","calloc","realloc","resize"},
            {"a.exe", "a.out", "a", "out.a"},
            {"(float)(3.14)(f)", "(f)(3.14)", "3.14f", "f(3.14)"},
            {"ltoa()", "ultoa()", "sprintf()", "None of the above"},

    };
    private String questions2[] = {
            "What of the following is the default value of a local variable?",
            "What is the size of long variable?",
            "What is the default value of byte variable?",
            "Which of the following is true about String?",
            "What is inheritance?"
    };
    private String options2[][] = {
            {"null","0","Depends upon the type of variable","Not assigned"},
            {"8 bit","16 bit","32 bit","64 bit"},
            {"0", "0.0", "null", "not defined"},
            {"String is mutable", "String is immutable", "String is a datatype", "None of the above"},
            {"It is the process where one object acquires the properties of another", "inheritance is the ability of an object to take on many forms", "inheritance is a technique to define different methods of same type", "None of the above"},

    };
    private String correctOptions[] = {"Lisp","realloc","a.out","3.14f","ltoa()"};
    private String correctOptions2[] = {"Not assigned","64 bit","0","String is immutable","It is the process where one object acquires the properties of another"};
    public String getQuestion(int a)
    {
        return questions[a];
    }
    public String getJavaQuestion(int a)
    {
        return questions2[a];
    }
    public String getOption1(int a)
    {
        return options[a][0];
    }
    public String getOption2(int a)
    {
        return options[a][1];
    }
    public String getOption3(int a)
    {
        return options[a][2];
    }
    public String getOption4(int a)
    {
        return options[a][3];
    }
    public String getJavaOption1(int a)
    {
        return options2[a][0];
    }
    public String getJavaOption2(int a)
    {
        return options2[a][1];
    }
    public String getJavaOption3(int a)
    {
        return options2[a][2];
    }
    public String getJavaOption4(int a)
    {
        return options2[a][3];
    }
    public String getCorrectOption(int a)
    {
        return correctOptions[a];
    }
    public String getCorrectJavaOption(int a)
    {
        return correctOptions2[a];
    }
}
