package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class],version = 1)
abstract class UserDB: RoomDatabase() {
    abstract fun userDAO(): UserDAO

    // Singleton'dan faydalanıyoruz eğer aynı işlem başka yerde de yapılıyorsa bir tarafta silinirken diğer tarafta güncelleme gibi bu problemlerin önüne geçmek hedefleniyor.
    // volatile ile instance nesnesi oluşturup ilk defa oluştulup oluşturulmamasına göre işlem yapıyoruz.
    companion object{
        @Volatile private var instance: UserDB? = null

        // any objesi agacımızın en altını temsil eder
        private val lock=Any()

        // instance varsa onu ddöndür yoksa senkronize olarak gerekli işlemleri yap
        operator fun invoke(context: Context)= instance ?: synchronized(lock){
            instance ?: dbCreate(context).also {
                instance =it
            }
        }
        private fun dbCreate(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            UserDB::class.java,"users"
        ).build()
    }
}