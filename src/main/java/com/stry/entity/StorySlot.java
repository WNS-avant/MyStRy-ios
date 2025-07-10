package com.stry.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StorySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imagePath;
    private String pdfPath;
    private String genre;
    private String subgenres;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String desc;

    
    @Column(nullable = false)
    private boolean isPublic = false;


    private LocalDateTime uploadedAt;
    
    @Column(nullable = false)
    private int downloadCount = 0;

    @Column(nullable = false)
    private int readCount = 0;

    
    public StorySlot() {}

    public StorySlot(String title, String pdfPath, String imagePath,String genre, String subgenres, String desc, boolean isPublic, LocalDateTime uploadedAt, int downloadCount, int readCount) {
        this.title = title;
        this.pdfPath = pdfPath;
        this.imagePath = imagePath;
        this.genre = genre;
        this.subgenres = subgenres;
        this.desc = desc;
        this.isPublic = isPublic;
        this.uploadedAt = uploadedAt;
        this.downloadCount = downloadCount;
        this.readCount = readCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getSubgenres() {
		return subgenres;
	}

	public void setSubgenres(String subgenres) {
		this.subgenres = subgenres;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
    
}

