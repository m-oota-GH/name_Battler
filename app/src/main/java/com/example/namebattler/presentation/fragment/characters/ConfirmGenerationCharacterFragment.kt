package com.example.namebattler.presentation.fragment.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.namebattler.R
import com.example.namebattler.databinding.OutputScreenBinding
import com.example.namebattler.function.HeaderFlag
import com.example.namebattler.function.JobEnum
import com.example.namebattler.presentation.fragment.header.HeaderFragment
import com.example.namebattler.function.viewModel.CharacterViewModel
import com.example.namebattler.function.viewModel.HeaderViewModel
import com.example.namebattler.function.viewModel.getViewModelFactory

/** キャラクター作成完了画面 **/
class ConfirmGenerationCharacterFragment: Fragment() {

    private lateinit var binding: OutputScreenBinding
    private val setCharacterViewModel : CharacterViewModel by viewModels{ getViewModelFactory() }
    private val headerViewModel: HeaderViewModel by viewModels{ getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = OutputScreenBinding.inflate(inflater, container, false).apply {
            //ヘッダー
            if (savedInstanceState == null){
                // FragmentManagerのインスタンス生成
                val fragmentManager: FragmentManager = parentFragmentManager
                // FragmentTransactionのインスタンスを取得
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                // インスタンスに対して張り付け方を指定する
                setCharacterViewModel.bindEditText.postValue(setCharacterViewModel.characterStatus.value!!.NAME)
                fragmentTransaction.replace(
                    R.id.header_area,
                    HeaderFragment()
                )
                // 張り付けを実行
                fragmentTransaction.commit()
            }

            //ヘッダー情報更新
            headerViewModel.headerText.postValue(getString(R.string.create_character))
            headerViewModel.outputFlag = HeaderFlag.NEW_CHARACTER_GENERATE

            if (savedInstanceState == null){
                // FragmentManagerのインスタンス生成
                val fragmentManager: FragmentManager = parentFragmentManager
                // FragmentTransactionのインスタンスを取得
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                // インスタンスに対して張り付け方を指定する

                //ステータス情報Fragment
                fragmentTransaction.replace(
                    R.id.container_area,
                    CharacterStatusDetailFragment()
                )
                //作成後ボタンFragment
                fragmentTransaction.replace(
                    R.id.button_area,
                    AfterGenerationMenuFragment()
                )
                // 張り付けを実行
                fragmentTransaction.commit()
            }
        }

        return binding.root
    }
}