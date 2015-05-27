package mil.nga.giat.asam.settings.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mil.nga.giat.asam.R;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_about_fragment, container, false);

        view.findViewById(R.id.info_email_text_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String[] recipients = { getString(R.string.info_fragment_email_address) };
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.info_fragment_email_subject_text));
                intent.setType("plain/text");
                startActivity(intent);
            }
        });

        return view;
    }
}
