package com.webaddicted.kotlinproject.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.webaddicted.kotlinproject.R
import com.webaddicted.kotlinproject.databinding.FrmSmsRetrieverBinding
import com.webaddicted.kotlinproject.global.common.KeyboardEventListener
import com.webaddicted.kotlinproject.global.common.visible
import com.webaddicted.kotlinproject.global.misc.AppSignatureHashHelper
import com.webaddicted.kotlinproject.global.services.SMSReceiver
import com.webaddicted.kotlinproject.view.base.BaseFragment

class SmsRetrieverFrm : BaseFragment() {
    private lateinit var mBinding: FrmSmsRetrieverBinding

    companion object {
        val TAG = SmsRetrieverFrm::class.java.simpleName
        fun getInstance(bundle: Bundle): SmsRetrieverFrm {
            val fragment = SmsRetrieverFrm()
            fragment.arguments = bundle
            return SmsRetrieverFrm()
        }
    }

    override fun getLayout(): Int {
        return R.layout.frm_sms_retriever
    }

    override fun onViewsInitialized(binding: ViewDataBinding?, view: View) {
        mBinding = binding as FrmSmsRetrieverBinding
        init()
        clickListener();
    }

    private fun init() {
        mBinding.toolbar.imgBack.visible()
        mBinding.toolbar.txtToolbarTitle.text = resources.getString(R.string.sms_retriever_title)
        SMSReceiver.requestData(object : SMSReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String) {
                mBinding.txtOtpIs.setText(getString(R.string.otp_code_is)+" : "+otp)
                mBinding.pinview.value = (otp)
            }

            override fun onOTPReceivedError(error: String) {
                mBinding.txtOtpIs.setText(error)
            }

        })
        startSMSListener()
    }

    private fun clickListener() {
        mBinding.toolbar.imgBack.setOnClickListener(this)
        mBinding.btnHashcode.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        KeyboardEventListener(activity as AppCompatActivity) { isKeyboardOpen: Boolean, softkeybordHeight: Int ->
            if (isKeyboardOpen)
                mBinding.space.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, softkeybordHeight)
            else mBinding.space.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
        }
    }
    /**
     *From OREO
     * Starts SmsRetriever, which waits for ONE matching SMS message until timeout
     * (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
     * action SmsRetriever#SMS_RETRIEVED_ACTION.
     */
    private fun startSMSListener() {
        try {
            val appSignatureHashHelper = AppSignatureHashHelper(activity)
            // This code requires one time to get Hash keys do comment and share key
            Log.d(TAG, "Apps Hash Key: " + appSignatureHashHelper.appSignatures.get(0))
            mBinding.txtHashCode.setText("Apps Hash Key : " + appSignatureHashHelper.appSignatures.get(0))
            val client = activity?.let { SmsRetriever.getClient(it) }

            val task = client?.startSmsRetriever()
            task?.addOnSuccessListener(OnSuccessListener<Void> {
                // API successfully started
            })

            task?.addOnFailureListener(OnFailureListener {
                // Fail to start API
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     *
     * change  FA+9qCX9VSu with generated by  appSignatureHashHelper  --VapplxJYFpi--
     * send this kind of sms -> FA+9qCX9VSu is hash code generated by appSignatureHashHelper
     * <#> Your ExampleApp code is: 123ABC78
    FA+9qCX9VSu
     *
     */
    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.img_back -> activity?.onBackPressed()
            R.id.btn_hashcode -> startSMSListener()
        }
    }
}

