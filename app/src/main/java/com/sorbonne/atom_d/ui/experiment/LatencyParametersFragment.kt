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
        val experimentSamples = view.findViewById<EditText>(R.id.Latency_Experiment_Samples)
        val customName = view.findViewById<CheckBox>(R.id.Latency_Experiment_Custom_Name)
        val submitButton = view.findViewById<Button>(R.id.Latency_Experiment_Submit)

        // -----------------------------------------------------
        // RecyclerView (existing latency experiments)
        // -----------------------------------------------------

        val latencyExperimentsAdapter = EntityAdapterSingleColumn(SingleColumnViewHolder.SingleColumnType.TextView, AdapterType.LatencyExperiments)
        CustomRecyclerView(
            requireContext(),
            view.findViewById(R.id.Latency_Experiment_RecyclerView),
            latencyExperimentsAdapter,
            CustomRecyclerView.CustomLayoutManager.LINEAR_LAYOUT
        ).getRecyclerView()
        viewModel.getAllLatencyExperiments().observe(requireActivity(), latencyExperimentsAdapter::submitList)

        // -----------------------------------------------------
        // Enable/Disable custom name input
        // -----------------------------------------------------
        customName.setOnClickListener {
            experimentName.isEnabled = customName.isChecked
        }

        // -----------------------------------------------------
        // Submit logic
        // -----------------------------------------------------
        submitButton.setOnClickListener {

            var mSamples = 1
            if (experimentSamples.text.isNotEmpty()) {
                mSamples = experimentSamples.text.toString().toIntOrNull() ?: 1
            }
            if (mSamples <= 0) mSamples = 1

            // Automatic or custom name
            val experimentTitle: String = if (customName.isChecked &&
                experimentName.text.isNotEmpty()
            ) {
                experimentName.text.toString()
            } else {
                "Latency experiment - N $mSamples"
            }

            // Create unmeasured latency experiment
            val latencyExperiment = LatencyExperiments(
                id = 0,
                expName = experimentTitle,
                samples = mSamples
            )

            try {
                viewModel.insertLatencyExperiment(latencyExperiment)

                // Reset UI
                experimentName.setText("")
                experimentSamples.setText("")
                customName.isChecked = false
                experimentName.isEnabled = false

            } catch (e: IOException) {
                Log.e(TAG, "Error inserting latency experiment: ${e.message}")
                Toast.makeText(requireContext(), "Database error", Toast.LENGTH_LONG).show()
            }
        }
    }
}
