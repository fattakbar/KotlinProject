package com.webaddicted.kotlinproject.view.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.kotlinproject.R
import com.webaddicted.kotlinproject.databinding.FrmLoginBinding
import com.webaddicted.kotlinproject.global.common.GlobalUtility
import com.webaddicted.kotlinproject.global.common.ValidationHelper
import com.webaddicted.kotlinproject.view.activity.HomeActivity
import com.webaddicted.kotlinproject.view.base.BaseFragment
import com.webaddicted.kotlinproject.viewModel.common.CommonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFrm : BaseFragment() {
    private lateinit var mBinding: FrmLoginBinding
    private val commonViewModel: CommonViewModel by viewModel()
    companion object {
        val TAG = LoginFrm::class.java.simpleName
        fun getInstance(bundle: Bundle): LoginFrm {
            val fragment = LoginFrm()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.frm_login
    }

    override fun initUI(binding: ViewDataBinding?, view: View) {
        mBinding = binding as FrmLoginBinding
        init()
        clickListener()
    }

    private fun init() {
        mBinding.edtEmail.setText("deepak@gmail.com")
        mBinding.edtPassword.setText("Test@12345")
    }
    override fun onResume() {
        super.onResume()
        addBlankSpace(mBinding.bottomSpace)
    }
    private fun clickListener() {
        mBinding.btnLogin.setOnClickListener(this)
        mBinding.btnSignup.setOnClickListener(this)
        mBinding.txtForgotPsw.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_login -> validate()
            R.id.btn_signup -> navigateScreen(SignupFrm.TAG)
            R.id.txt_forgot_psw -> {
            }
        }
    }

    private fun validate() {
        if (ValidationHelper.validateEmail(mBinding.edtEmail, mBinding.wrapperEmail) &&
            ValidationHelper.validatePwd(mBinding.edtPassword, mBinding.wrapperPassword)) {
            val userInfo = commonViewModel.getCouponsBySize(mBinding.edtEmail.text.toString())
            when {
                userInfo==null -> GlobalUtility.showToast(resources.getString(R.string.create_an_account))
                userInfo.password.equals(mBinding.edtPassword.text.toString()) -> activity?.let { HomeActivity.newIntent(it) }
                else -> GlobalUtility.showToast(resources.getString(R.string.please_enter_correct_password))
            }
        }
    }

    /**
     * navigate on fragment
     *
     * @param tag represent navigation activity
     */
    private fun navigateScreen(tag: String) {
        var frm: Fragment? = null
        when (tag) {
            SignupFrm.TAG -> frm = SignupFrm.getInstance(Bundle())
        }
        if (frm != null) navigateAddFragment(R.id.container, frm, true)
    }

}
