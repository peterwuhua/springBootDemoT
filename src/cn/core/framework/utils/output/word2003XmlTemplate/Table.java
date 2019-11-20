package cn.core.framework.utils.output.word2003XmlTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 注意：该类中所有的方法接收的索引均是从零开始计数
 */
public class Table implements Iterable<List<Cell>> {
	
	//表数据
	private List<List<Cell>> tableData;
	
	public Table(List<List<String>> tableData) {
		this.tableData = this.getFormalTable(tableData);
	}
	
	public Table() {
		this.tableData = new ArrayList<List<Cell>>();
	}

	/**
	 * 将数据放到Cell对象中， 以便保存单元格数据的元信息
	 * @param rawTable 原始的数据
	 * @return 被封装的数据
	 */
	private List<List<Cell>> getFormalTable(List<List<String>> rawTable) {
		List<List<Cell>> table = new ArrayList<List<Cell>>();
		for (List<String> rawRow : rawTable) {
			List<Cell> row = new ArrayList<Cell>();
			for (String content : rawRow) {
				row.add(new Cell(content));
			}
			table.add(row);
		}
		return table;
	}
	
	/**
	 * 合并单元格，假如单元格要被合并掉，那么这个Cell对象不会被在这个方法中被删除，但是它的状态会被置为已删除。
	 * 
	 * @param table 合并这个表中的单元格
	 * @param startRowIndex 横向合并的起始位置
	 * @param endRowIndex 横向合并的结束位置
	 * @param startColumnIndex 纵向合并的起始位置
	 * @param endColumnIndex 纵向合并的结束位置
	 */
	public void mergeCells(int startRowIndex, int endRowIndex, int startColumnIndex, int endColumnIndex) {
		//Merge in x-axis
		for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
			for (int columnIndex = startColumnIndex + 1; columnIndex <= endColumnIndex; columnIndex++) {
				Cell current = this.tableData.get(rowIndex).get(columnIndex);
				Cell previous = this.tableData.get(rowIndex).get(columnIndex - 1);
				//假如当前单元格和前一个单元格的内容相同， 那么它会持有合并开始的那个单元格，并且它会被置为已删除状态，并且他的
				if (current.equals(previous)) {
					current.setXAxisHead(previous);
				}
			}
		}
		//Merge in y-axis
		for (int columnIndex = startColumnIndex; columnIndex <= endColumnIndex; columnIndex++) {
			for (int rowIndex = startRowIndex + 1; rowIndex <= endRowIndex; rowIndex++) {
				Cell current = this.tableData.get(rowIndex).get(columnIndex);
				Cell previous = this.tableData.get(rowIndex - 1).get(columnIndex);
				//假如当前单元格和前一个单元格的内容相同， 那么它会持有合并开始的那个单元格，并且它会被置为已删除状态，并且他的
				if (current.equals(previous)) {
					current.setYAxisHead(previous);
				}
			}
		}
	}
	
	public void mergeCellsInXAxis(int startRowIndex, int endRowIndex) {
        mergeCells(startRowIndex, endRowIndex, 0, this.getColumnCount() - 1);
	}
	
	public void mergeCellsInXAxis(int rowIndex) {
        mergeCellsInXAxis(rowIndex, rowIndex);
	}
	
	public void mergeCellsInXAxisOnTop(int rowNumber) {
        mergeCellsInXAxis(0, rowNumber - 1);
	}
	
	public void mergeCellsInYAxis(int startColumnIndex, int endColumnIndex) {
        mergeCells(0, this.getRowCount() - 1, startColumnIndex, endColumnIndex);
	}
	
	public void mergeCellsInYAxis(int columnIndex) {
        mergeCellsInYAxis(columnIndex, columnIndex);
	}
	
	public void mergeCellsInYAxisOnLeft(int columnNumber) {
        mergeCellsInYAxis(0, columnNumber - 1);
	}
	
	/**
	 * 为一个表中的某个区域的单元格设置宽度
	 * 
	 * @param table 从这个表中寻找要设置的区域
	 * @param width 单元格宽度
	 * @param table 合并这个表中的单元格
	 * @param startRowIndex 横向合并的起始位置
	 * @param endRowIndex 横向合并的结束位置
	 * @param startColumnIndex 纵向合并的起始位置
	 * @param endColumnIndex 纵向合并的结束位置
	 */
	public void setCellsWidth(int width, int startRowIndex, int endRowIndex, int startColumnIndex, int endColumnIndex) {
		for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
			for (int columnIndex = startColumnIndex; columnIndex <= endColumnIndex; columnIndex++) {
				this.tableData.get(rowIndex).get(columnIndex).setWidth(width);
			}
		}
	}
	
	public void setCellsWidthInYAxis(int width, int startColumnIndex, int endColumnIndex) {
		setCellsWidth(width, 0, this.getRowCount() - 1, startColumnIndex, endColumnIndex);
	}
	
	public void setCellsWidthInYAxis(int width, int columnIndex) {
		setCellsWidthInYAxis(width, columnIndex, columnIndex);
	}

	@Override
	public Iterator<List<Cell>> iterator() {
		return this.tableData.iterator();
	}
	
	public int getRowCount() {
		return this.tableData.size();
	}
	
	public int getColumnCount() {
		return this.tableData.get(0).size();
	}
	
	public void addOneColumnData(List<String> columnData) {
		for (int i = 0; i < columnData.size(); i++) {
			if (this.tableData.size() < i + 1) {
				this.tableData.add(new ArrayList<Cell>());
			}
			this.tableData.get(i).add(new Cell(columnData.get(i)));
		}
	}
}












