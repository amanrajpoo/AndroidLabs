package com.example.nasaimageoftheday;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nasaimageoftheday.FavoriteImage;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(FavoriteImage favoriteImage);

    @Delete
    void delete(FavoriteImage favoriteImage);

    @Query("SELECT * FROM FavoriteImage")
    List<FavoriteImage> getAllFavorites();
}
