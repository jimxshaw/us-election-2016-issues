package me.jimmyshaw.politicalpositions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

// IssueListActivity will host this IssueListFragment that contains a RecyclerView with its two usual
// utility classes, a ViewHolder and an Adapter. This Fragment's purpose to show our list of issues
// retrieved from IssueLab. A ViewHolder holds on to a view. An Adapter creates ViewHolders. It's a
// controller object that sits between the RecyclerView and the data set that the RecyclerView
// should display. Its responsibilities are creating the necessary ViewHolders and binding
// ViewHolders to data from the model layer.
public class IssueListFragment extends Fragment {

    private RecyclerView mIssueRecyclerView;
    private IssueAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_list, container, false);

        mIssueRecyclerView = (RecyclerView) view.findViewById(R.id.issue_recycler_view);
        mIssueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        IssueLab issueLab = IssueLab.get(getActivity());

        List<Issue> issues = issueLab.getIssues();

        mAdapter = new IssueAdapter(issues);
        mIssueRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_issue_list, menu);

        MenuItem aboutScreenItem = menu.findItem(R.id.menu_item_about_screen);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_about_screen:
                Intent intent = new Intent(getContext(), AboutScreenActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class IssueHolder extends RecyclerView.ViewHolder
                                implements View.OnClickListener {
        private Issue mIssue;
        private TextView mTitleTextView;
        private ImageView mIconImageView;

        public IssueHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_issue_title_text_view);
            mIconImageView = (ImageView) itemView.findViewById(R.id.list_item_issue_icon_image_view);
        }

        public void bindIssue(Issue issue) {
            mIssue = issue;
            mTitleTextView.setText(issue.getTitle());

            switch (issue.getTitle()) {
                case "Energy":
                    mIconImageView.setImageResource(R.drawable.ic_issues_energy);
                    break;
                case "Veterans":
                    mIconImageView.setImageResource(R.drawable.ic_issues_veterans);
                    break;
                case "Taxes":
                    mIconImageView.setImageResource(R.drawable.ic_issues_taxes);
                    break;
                case "Defense":
                    mIconImageView.setImageResource(R.drawable.ic_issues_defense);
                    break;
                case "Health care":
                    mIconImageView.setImageResource(R.drawable.ic_issues_health_care);
                    break;
                case "Budget and spending":
                    mIconImageView.setImageResource(R.drawable.ic_issues_budget_spending);
                    break;
                case "Foreign policy":
                    mIconImageView.setImageResource(R.drawable.ic_issues_foreign_policy);
                    break;
                case "Civil liberties":
                    mIconImageView.setImageResource(R.drawable.ic_issues_civil_liberties);
                    break;
                case "Education":
                    mIconImageView.setImageResource(R.drawable.ic_issues_education);
                    break;
                case "Crime and safety":
                    mIconImageView.setImageResource(R.drawable.ic_issues_crime_safety);
                    break;
                case "Environment":
                    mIconImageView.setImageResource(R.drawable.ic_issues_environment);
                    break;
                case "Immigration":
                    mIconImageView.setImageResource(R.drawable.ic_issues_immigration);
                    break;
                case "Economy":
                    mIconImageView.setImageResource(R.drawable.ic_issues_economy);
                    break;
                case "Abortion":
                    mIconImageView.setImageResource(R.drawable.ic_issues_abortion);
                    break;
                default:
                    break;
            }
        }

        // When a particular issue is clicked from our list of issues, an intent is fired to start
        //
        @Override
        public void onClick(View v) {
            Intent intent = QuotePagerActivity.newIntent(getActivity(), mIssue.getId());
            startActivity(intent);
        }
    }

    private class IssueAdapter extends RecyclerView.Adapter<IssueHolder> {

        private List<Issue> mIssues;

        public IssueAdapter(List<Issue> issues) {
            mIssues = issues;
        }

        @Override
        public IssueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_issue, parent, false);
            return new IssueHolder(view);
        }

        @Override
        public void onBindViewHolder(IssueHolder holder, int position) {
            Issue issue = mIssues.get(position);
            holder.bindIssue(issue);
        }

        @Override
        public int getItemCount() {
            return mIssues.size();
        }
    }
}
