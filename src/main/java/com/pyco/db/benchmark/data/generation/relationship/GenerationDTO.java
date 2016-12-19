package com.pyco.db.benchmark.data.generation.relationship;

/**
 * Created by pyco on 12/1/16.
 */
public class GenerationDTO {

    private int startRow;
    private int endRow;
    private int randomAmountStart;
    private int randomAmountEnd;
    private int randomArrange;

    public GenerationDTO() {
    }

    public GenerationDTO(int startRow, int endRow, int randomAmountStart, int randomAmountEnd, int randomArrange) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.randomAmountStart = randomAmountStart;
        this.randomArrange = randomArrange;
        this.randomAmountEnd = randomAmountEnd;
    }

    public int getRandomAmountEnd() {
        return randomAmountEnd;
    }

    public void setRandomAmountEnd(int randomAmountEnd) {
        this.randomAmountEnd = randomAmountEnd;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getRandomAmountStart() {
        return randomAmountStart;
    }

    public void setRandomAmountStart(int randomAmountStart) {
        this.randomAmountStart = randomAmountStart;
    }

    public int getRandomArrange() {
        return randomArrange;
    }

    public void setRandomArrange(int randomArrange) {
        this.randomArrange = randomArrange;
    }

}
