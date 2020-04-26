package com.github.tarcv.orderme.app.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber

abstract class LifecycleLogFragment : Fragment() {
    init {
        Timber.tag(this::class.java.simpleName)
        Timber.i("ctor")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag(this::class.java.simpleName)
        Timber.i("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.i("onCreate")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(this::class.java.simpleName)
        Timber.i("onPause")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.i("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(this::class.java.simpleName)
        Timber.i("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(this::class.java.simpleName)
        Timber.i("onResume")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.tag(this::class.java.simpleName)
        Timber.i("onDetach")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(this::class.java.simpleName)
        Timber.i("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag(this::class.java.simpleName)
        Timber.i("onDestroyView")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(this::class.java.simpleName)
        Timber.i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(this::class.java.simpleName)
        Timber.i("onDestroy")
    }
}