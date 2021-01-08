package com.example.namebattler.characters.activity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.namebattler.R
import com.example.namebattler.characters.fragment.CharacterStatusDetailFragment
import com.example.namebattler.data.characterData.CharacterHolder
import com.example.namebattler.menu.HeaderFragment
import com.example.namebattler.util.ScopedAppActivity

//キャラクター詳細画面
class ConfirmCharacterStatusActivity   : ScopedAppActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.output_screen)

        //ヘッダー
        if (savedInstanceState == null){
            // FragmentManagerのインスタンス生成
            val fragmentManager: FragmentManager = supportFragmentManager
            // FragmentTransactionのインスタンスを取得
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            // インスタンスに対して張り付け方を指定する
            fragmentTransaction.replace(
                R.id.header_area,
                HeaderFragment.newInstance(
                    "キャラ詳細"
                )
            )
            // 張り付けを実行
            fragmentTransaction.commit()
        }

        // Serializableを使ってobjectをFragmentへ渡す
        if (savedInstanceState == null) {
            val sendObj = intent.getSerializableExtra(CharacterHolder.EXTRA_DATA)

            // FragmentManagerのインスタンス生成
            val fragmentManager: FragmentManager = supportFragmentManager
            // FragmentTransactionのインスタンスを取得
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            // インスタンスに対して張り付け方を指定する
            fragmentTransaction.replace(
                R.id.container_area,
                CharacterStatusDetailFragment()
//                CommonDisplayStatusFragment.newInstance(
//                    sendObj as CharacterHolder
//                )
            )

//            fragmentTransaction.replace(
//                R.id.button_area,
//                ProcessingDataDeletionFragment.newInstance(
//                    sendObj
//                )
//            )
            // 張り付けを実行
            fragmentTransaction.commit()
        }
    }

}