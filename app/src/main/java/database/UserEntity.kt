package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="userId")
    var userid : Int=0,

    @ColumnInfo(name = "userEmail")
    val email:String,

    @ColumnInfo(name="userName")
    val username:String,

    @ColumnInfo(name = "userPassword")
    val password:String,

    @ColumnInfo(name = "userScore")
    val score:Int=0,
    )