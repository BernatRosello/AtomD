package com.sorbonne.atom_d.ui.experiment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sorbonne.atom_d.R
import com.sorbonne.atom_d.adapters.AdapterType
import com.sorbonne.atom_d.adapters.single_column.EntityAdapterSingleColumn
import com.sorbonne.atom_d.entities.DatabaseRepository
import com.sorbonne.atom_d.entities.latency_experiments.LatencyExperiments
import com.sorbonne.atom_d.tools.CustomRecyclerView
import com.sorbonne.atom_d.view_holders.SingleColumnViewHolder
import java.io.IOException

class LatencyParametersFragment : Fragment() {

    private val TAG = LatencyParametersFragment::class.simpleName

    private val viewModel: ExperimentViewModel by viewModels {
        ExperimentViewModelFactory(DatabaseRepository(requireActivity().application))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_latency_parameters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // -----------------------------------------------------
        // UI References
        // -----------------------------------------------------
        val experimentName = view.findViewById<EditText>(R.id.Latency_Experiment_Name)
        val experimentTries = view.findViewById<EditText>(R.id.Latency_Experiment_Tries)
        val customName = view.findViewById<CheckBox>(R.id.Latency_Experiment_Custom_Name)
        val submitButton = view.findViewById<Button>(R.id.Latency_Experiment_Submit)

        // -----------------------------------------------------
        // RecyclerView
        // -----------------------------------------------------
        val latencyAdapter = EntityAdapterSingleColumn(
            SingleColumnViewHolder.SingleColumnType.TextView,
            AdapterType.LatencyExperiments
        )

        CustomRecyclerView(
            requireContext(),
            view.findViewById(R.id.Latency_Experiment_RecyclerView),
            latencyAdapter,
            CustomRecyclerView.CustomLayoutManager.LINEAR_LAYOUT
        ).getRecyclerView()

        viewModel.getAllLatencyExperiments().observe(requireActivity(), latencyAdapter::submitList)

        // -----------------------------------------------------
        // Custom name enable / disable
        // -----------------------------------------------------
        customName.setOnClickListener {
            experimentName.isEnabled = customName.isChecked
        }

        // -----------------------------------------------------
        // Submit button logic
        // -----------------------------------------------------
        submitButton.setOnClickListener {

            var newLatencyExperiment: LatencyExperiments? = null

            // Number of tries (optional)
            var mTries = 1
            if (experimentTries.text.isNotEmpty()) {
                mTries = try {
                    experimentTries.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    1
                }
            }
            if (mTries <= 0) mTries = 1

            // Name
            val mName: String = if (customName.isChecked && experimentName.text.isNotEmpty()) {
                experimentName.text.toString()
            } else {
                "Latency experiment - N $mTries"
            }

            // Create DB Entity (internal latency fields are NOT set here)
            newLatencyExperiment = LatencyExperiments(
                id = 0,
                expName = mName,
                tries = mTries,
                // Latency measurements happen later
                avgLatency = 0.0,
                minLatency = 0.0,
                maxLatency = 0.0,
                sdLatency = 0.0
            )

            // Insert into DB
            try {
                viewModel.insertLatencyExperiment(newLatencyExperiment)

                // Reset UI
                experimentName.setText("")
                experimentTries.setText("")
                customName.isChecked = false
                experimentName.isEnabled = false

            } catch (e: IOException) {
                Log.e(TAG, "Error inserting latency experiment: ${e.message}")
                Toast.makeText(requireContext(), "Database error", Toast.LENGTH_LONG).show()
            }
        }
    }
}
