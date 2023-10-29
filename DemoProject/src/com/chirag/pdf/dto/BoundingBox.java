package com.chirag.pdf.dto;

public class BoundingBox {
	private Double topLeftX;
	private Double topLeftY;
	private Double topRightX;
	private Double topRightY;
	private Double bottomRightX;
	private Double bottomRightY;
	private Double bottomLeftX;
	private Double bottomLeftY;

	public BoundingBox() {
	}

	public BoundingBox(Double topLeftX, Double topLeftY,
                       Double topRightX, Double topRightY,
                       Double bottomRightX, Double bottomRightY,
                       Double bottomLeftX, Double bottomLeftY) {
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.topRightX = topRightX;
		this.topRightY = topRightY;
		this.bottomRightX = bottomRightX;
		this.bottomRightY = bottomRightY;
		this.bottomLeftX = bottomLeftX;
		this.bottomLeftY = bottomLeftY;
	}

	// for top left
	public Double getTopLeftX() {
		return topLeftX;
	}

	public void setTopLeftX(Double topLeftX) {
		this.topLeftX = topLeftX;
	}

	public Double getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(Double topLeftY) {
		this.topLeftY = topLeftY;
	}
	
	// for top right
	public Double getTopRightX() {
		return topRightX;
	}

	public void setTopRightX(Double topRightX) {
		this.topRightX = topRightX;
	}

	public Double getTopRightY() {
		return topRightY;
	}

	public void setTopRightY(Double topRightY) {
		this.topRightY = topRightY;
	}
	
	// for bottom right
	public Double getBottomRightX() {
		return bottomRightX;
	}

	public void setBottomRightX(Double bottomRightX) {
		this.bottomRightX = bottomRightX;
	}

	public Double getBottomRightY() {
		return bottomRightY;
	}

	public void setBottomRightY(Double bottomRightY) {
		this.bottomRightY = bottomRightY;
	}
	
	// for bottom left
	public Double getBottomLeftX() {
		return bottomLeftX;
	}

	public void setBottomLeftX(Double bottomLeftX) {
		this.bottomLeftX = bottomLeftX;
	}

	public Double getBottomLeftY() {
		return bottomLeftY;
	}

	public void setBottomLeftY(Double bottomLeftY) {
		this.bottomLeftY = bottomLeftY;
	}
}
