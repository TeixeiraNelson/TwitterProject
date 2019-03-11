package ch.ribeiropython.twitterproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tweets")
public class TweetEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo (name = "message")
    private String message;

    @ColumnInfo (name="idUser")
    private Long idUser;

    @ColumnInfo (name = "hashtags")
    private String[] hashtags;

}
