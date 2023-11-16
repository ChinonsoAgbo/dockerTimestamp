package org.example.modal.result;

import org.example.modal.result.Result;

import java.util.List;

public class ResultData {
    private List<Result> result;

    public ResultData(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}