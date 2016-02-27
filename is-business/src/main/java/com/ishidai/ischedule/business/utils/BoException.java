package com.ishidai.ischedule.business.utils;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BoException extends RuntimeException {

    private Throwable cause;

    private static final long serialVersionUID = 479788007437470106L;

    public BoException(String msg) {
        super(msg);
    }

    public BoException(Throwable ex) {
        this.cause = ex;
    }

    public BoException(String msg, Throwable ex) {
        super(msg);
        this.cause = ex;

    }

    public Throwable getCause() {
        return (this.cause == this ? null : this.cause);
    }

    public String getMessage() {
        if (this.cause == null || this.cause == this) {
            return super.getMessage();
        } else {
            return super.getMessage() + "; Schedule exception is " + this.cause.getClass().getName() + ": "
                    + this.cause.getMessage();
        }
    }

    public void printStackTrace(PrintStream ps) {
        if (this.cause == null || this.cause == this) {
            super.printStackTrace(ps);
        } else {
            ps.println(this);
            this.cause.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        if (this.cause == null || this.cause == this) {
            super.printStackTrace(pw);
        } else {
            pw.println(this);
            this.cause.printStackTrace(pw);
        }
    }
}
