package com.sasanka.movies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for a movie or tv series.
 */

public class SearchResult implements Parcelable {

    private Integer id;
    private String originalTitle;
    private String title;
    private String overview;
    private String mediaType;
    private String genres;
    private String imageUrl_w92;
    private String imageUrl_w154;
    private String originalLanguage;
    private String releaseYear;
    private String releaseDate;
    private Double popularity;
    private Double voteAverage;
    private Integer voteCount;

    public static final Parcelable.Creator<SearchResult> CREATOR = new Parcelable.Creator<SearchResult>() {
        public SearchResult createFromParcel(Parcel source) {
            return new SearchResult(source);
        }

        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public SearchResult() {
    }

    private SearchResult(Parcel in) {
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.mediaType = in.readString();
        this.genres = in.readString();
        this.imageUrl_w92 = in.readString();
        this.imageUrl_w154 = in.readString();
        this.originalLanguage = in.readString();
        this.releaseYear = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.mediaType);
        dest.writeString(this.genres);
        dest.writeString(this.imageUrl_w92);
        dest.writeString(this.imageUrl_w154);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.releaseYear);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.voteAverage);
        dest.writeInt(this.voteCount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSmallImageUrl() {
        return imageUrl_w92;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.imageUrl_w92 = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return imageUrl_w154;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.imageUrl_w154 = largeImageUrl;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseDate) {
        this.releaseYear = releaseDate;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
