package ba.klika.androidtemplate.ui.base.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerDialogFragment

/**
 * Usage:
 * Get reference to the Fragment manager
 *
 * FragmentManager fragmentManager = getSupportFragmentManager();
 *
 * Instantiate the dialog
 *
 * Show the dialog
 * dialog.show(fragmentManager, TAG);
 *
 * ViewModels are not provided for dialogs right now, since these should be as simple as possible
 *
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseDialog : DaggerDialogFragment() {
    // region public inteface =======================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        onCreated(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = View.inflate(activity, layoutRId, null)
        builder.setView(view)
        val dialog = builder.create()

        isCancelable = isCancellationEnabled()
        dialog.setCanceledOnTouchOutside(isCancellationEnabled())

        setupViews(view)

        return dialog
    }
    // endregion

    // region overrideable methods ==================================================================
    protected open fun onCreated(savedInstanceState: Bundle?) {
    }

    protected abstract val layoutRId: Int

    /**
     * Override and return true if dialog should be cancelable
     */
    protected fun isCancellationEnabled(): Boolean {
        return false
    }

    /**
     * Override to perform operations on [View] as soon as it is created
     * There is no need to perform Butterknife.bind() since it is already done inside this dialog
     */
    protected open fun setupViews(view: View) {
    }
    // endregion
}