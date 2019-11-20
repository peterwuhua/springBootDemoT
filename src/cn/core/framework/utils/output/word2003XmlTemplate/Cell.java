package cn.core.framework.utils.output.word2003XmlTemplate;

public class Cell {
	
	private Object value;
	private int colspan = 1, rowspan = 1;
	private Cell xAxisHead, yAxisHead;
	private Integer width = null;
	
	public Cell(Object value) {
		this.value = value;
	}
	
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public Object getWidth() {
		return this.width == null ? "" : this.width;
	}

	public void setXAxisHead(Cell previous) {
		while (previous.getXAxisHead() != null) {
			previous = previous.getXAxisHead();
		}
		this.xAxisHead = previous;
		if (this.colspan > 0) {
			this.xAxisHead.increaseColspanOnXAxis();
			this.colspan = 0;	
		}
	}

	public Cell getXAxisHead() {
		return this.xAxisHead;
	}
	
	public void setYAxisHead(Cell previous) {
		while (previous.getYAxisHead() != null) {
			previous = previous.getYAxisHead();
		}
		this.yAxisHead = previous;
		if (this.rowspan > 0) {
			this.yAxisHead.increaseRowspanOnYAxis();
			this.rowspan = 0;	
		}
	}

	public Cell getYAxisHead() {
		return this.yAxisHead;
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
	
	public void increaseColspanOnXAxis() {
		this.colspan++;
	}
	
	public void increaseRowspanOnYAxis() {
		this.rowspan++;
	}
	
	public int getColspan() {
		return this.colspan;
	}

	public String getWVmergeTagTemplate() {
		if (this.rowspan == 1) {
			return "";
		} else if (this.rowspan == 0) {
			return "<w:vmerge/>";
		} else {
			return "<w:vmerge w:val=\"restart\"/>";
		}
	}

	@Override
	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}
}