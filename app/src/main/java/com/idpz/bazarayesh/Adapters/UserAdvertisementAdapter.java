package com.idpz.bazarayesh.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idpz.bazarayesh.Models.AdWorkShop.Ad;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.Utils.Tools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;


public class UserAdvertisementAdapter extends RecyclerView.Adapter<UserAdvertisementAdapter.NewsViewHolder> {


    private int lastPosition = -1;
    private Context context;
    Activity activity;
    Tools tools;
    Typeface typeface;
    int layout;

    List<Ad> workshops;
    List<com.idpz.bazarayesh.Models.AdDisscount.Ad> disscounts;
    List<com.idpz.bazarayesh.Models.AdAssignment.Ad> assignments;
    List<com.idpz.bazarayesh.Models.AdRecruiment.Ad> recruiments;
    List<com.idpz.bazarayesh.Models.AdBride.Ad> brides;
    List<com.idpz.bazarayesh.Models.AdRegCourse.Ad> regcourses;


    public UserAdvertisementAdapter(List<Ad> workshops, Context context, int layout) {
        this.workshops = workshops;
        this.context = context;
        this.typeface = typeface;
        this.activity = (Activity) context;
        this.layout = layout;
        tools = new Tools(context);
    }

    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdDisscount.Ad> disscounts, int layout, Context context, int lastPosition, int a) {
        this.disscounts = disscounts;
        this.layout = layout;
        this.context = context;
        tools = new Tools(context);
    }


    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdBride.Ad> brides, int layout, Context context, int lastPosition) {
        this.brides = brides;
        this.layout = layout;
        this.context = context;
        tools = new Tools(context);

    }


    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdAssignment.Ad> assignments, int layout, Context context) {
        this.assignments = assignments;
        this.layout = layout;
        this.context = context;
        tools = new Tools(context);

    }

    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdRecruiment.Ad> recruiments, Context context, int layout, int lastPosition) {
        this.recruiments = recruiments;
        this.layout = layout;
        this.context = context;
        tools = new Tools(context);

    }

    public UserAdvertisementAdapter(int a, List<com.idpz.bazarayesh.Models.AdRegCourse.Ad> regcourses, Context context, int layout) {
        this.regcourses = regcourses;
        this.layout = layout;
        this.context = context;
        tools = new Tools(context);

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);


        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {


        switch (layout) {


            case R.layout.workshop_adapter:

                try {
                    holder.text1.setText(workshops.get(position).getSubject());
                    holder.text2.setText(workshops.get(position).getDescription());

                    holder.rel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialogWork(workshops.get(position).getDate(), workshops.get(position).getCourse(), workshops.get(position).getSubject(), workshops.get(position).getEvidence(), workshops.get(position).getDescription());
                        }
                    });


                    holder.trash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeAds(position, "workshops", workshops.get(position).getId());

                        }
                    });
                } catch (Exception e) {
                }


                break;


            case R.layout.recruiment_adapter:
                try {

                    if (recruiments != null) {
                        holder.text1.setText(recruiments.get(position).getSubject());
                        holder.text2.setText(recruiments.get(position).getDescription());
                        holder.rel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                AlertDialogRec(recruiments.get(position).getSubject(), recruiments.get(position).getExpertise(), recruiments.get(position).getLvl(), recruiments.get(position).getConditions(), recruiments.get(position).getDescription());
                            }
                        });


                        holder.trash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                removeAds(position, "recruiments", recruiments.get(position).getId());

                            }
                        });


                    } else {

                        holder.text1.setText(regcourses.get(position).getCourseName());
                        holder.text2.setText(regcourses.get(position).getDescription());
                        holder.rel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialogRegCourse("از تاریخ " + regcourses.get(position).getScourse() + " تا تاریخ", regcourses.get(position).getDuration(), regcourses.get(position).getTopic(), regcourses.get(position).getCourseName(), regcourses.get(position).getEvidence(), regcourses.get(position).getDescription());
                            }
                        });


                        holder.trash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                removeAds(position, "courses", regcourses.get(position).getId());

                            }
                        });
                    }

                } catch (Exception e) {
                    e.getMessage();
                }
                break;
            case R.layout.assignment_adapter:

                try {

                    holder.text1.setText(assignments.get(position).getType() + " " + assignments.get(position).getOptions());
                    holder.text2.setText(assignments.get(position).getDescription());
                    holder.rel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialogAssignMent(assignments.get(position).getType(), assignments.get(position).getOptions(), assignments.get(position).getDescription());
                        }
                    });


                    holder.trash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeAds(position, "assignments", assignments.get(position).getId());

                        }
                    });
                } catch (Exception e) {
                }
                break;
            case R.layout.disscounts_adapter:
                try {
                    if (disscounts != null) {
                        holder.text1.setText(disscounts.get(position).getAffair());
                        holder.text2.setText(disscounts.get(position).getDescription());

                        holder.rel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialogDisscount(disscounts.get(position).getSdate(), disscounts.get(position).getEdate(), disscounts.get(position).getDiscount(), disscounts.get(position).getAdEvent(), disscounts.get(position).getAffair(), disscounts.get(position).getDescription());
                            }
                        });

                        holder.trash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                removeAds(position, "Discount_ads", disscounts.get(position).getId());

                            }
                        });

                    } else {


                        holder.text1.setText("درتاریخ " + brides.get(position).getDate());
                        holder.text2.setText(brides.get(position).getDescription());

                        holder.rel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                AlertDialogAssignMent(" درتاریخ " + brides.get(position).getDate(), " از ساعت " + brides.get(position).getShour() + " تا ساعت " + brides.get(position).getEhour(), brides.get(position).getDescription());
                            }
                        });


                        holder.trash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                removeAds(position, "brides", brides.get(position).getId());

                            }
                        });

                    }

                } catch (Exception e) {

                    e.getMessage();
                }
                break;

        }


    }

    @Override
    public int getItemCount() {
        switch (layout) {
            case R.layout.assignment_adapter:
                return assignments.size();
            case R.layout.recruiment_adapter:
                if (recruiments != null)
                    return recruiments.size();
                else return regcourses.size();

            case R.layout.workshop_adapter:
                return workshops.size();

            case R.layout.disscounts_adapter:


                if (disscounts != null)
                    return disscounts.size();
                else return brides.size();

        }
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        // tarife view ha

        TextView text1, text2;

        RelativeLayout rel;

        ImageView trash;

        public NewsViewHolder(View itemView) {
            // bind karadne view ha
            super(itemView);


            switch (layout) {
                case R.layout.assignment_adapter:
                    text1 = itemView.findViewById(R.id.text1);
                    text2 = itemView.findViewById(R.id.text2);
                    trash = itemView.findViewById(R.id.trash);

                    rel = itemView.findViewById(R.id.rel);

                    break;
                case R.layout.recruiment_adapter:

                    text1 = itemView.findViewById(R.id.text1);
                    text2 = itemView.findViewById(R.id.text2);
                    trash = itemView.findViewById(R.id.trash);

                    rel = itemView.findViewById(R.id.rel);
                    break;
                case R.layout.disscounts_adapter:
                    text1 = itemView.findViewById(R.id.text1);
                    text2 = itemView.findViewById(R.id.text2);
                    trash = itemView.findViewById(R.id.trash);

                    rel = itemView.findViewById(R.id.rel);

                    break;
                case R.layout.workshop_adapter:
                    text1 = itemView.findViewById(R.id.text1);
                    text2 = itemView.findViewById(R.id.text2);
                    trash = itemView.findViewById(R.id.trash);

                    rel = itemView.findViewById(R.id.rel);

                    break;


            }

        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public void AlertDialogRec(String title, String subject, String lvl, String terms, String desc) {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.message_details_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtSubject = message.findViewById(R.id.txtSubject);
        TextView txtlvl = message.findViewById(R.id.txtlvl);
        TextView txtTerms = message.findViewById(R.id.txtTerms);
        TextView txtDesc = message.findViewById(R.id.txtDesc);


        TextView btnExit = message.findViewById(R.id.btnExit);
        txtTitle.setText(" عنوان تخصص:" + title);
        txtSubject.setText(" موضوع تخصص: " + subject);
        txtlvl.setText(" سطح تخصص: " + lvl);
        txtTerms.setText(" شرایط: " + terms);
        txtDesc.setText(desc);

        //txtBody.setText();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void AlertDialogWork(String day, String corse, String subj, String type, String desc) {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.message_details_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtSubject = message.findViewById(R.id.txtSubject);
        TextView txtlvl = message.findViewById(R.id.txtlvl);
        TextView txtTerms = message.findViewById(R.id.txtTerms);
        TextView txtDesc = message.findViewById(R.id.txtDesc);


        TextView btnExit = message.findViewById(R.id.btnExit);
        txtTitle.setText(" روز: " + day);
        txtSubject.setText("  مبحث: " + corse);
        txtlvl.setText("  موضوع کارگاه: " + subj);
        txtTerms.setText(" نوع مدرک: " + type);
        txtDesc.setText(desc);

        //txtBody.setText();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void AlertDialogDisscount(String since, String till, String percent, String ad_event, String work, String desc) {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.message_details_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtSubject = message.findViewById(R.id.txtSubject);
        TextView txtlvl = message.findViewById(R.id.txtlvl);
        TextView txtTerms = message.findViewById(R.id.txtTerms);
        TextView txtDesc = message.findViewById(R.id.txtDesc);


        TextView btnExit = message.findViewById(R.id.btnExit);
        txtTitle.setText(" از تاریخ: " + since
                + "  تا تاریخ: " + till);
        txtSubject.setText("انجام امور: " + work);
        txtlvl.setText("درصد تخفیف: " + percent);
        txtTerms.setText("به مناسبت : " + ad_event);
        txtDesc.setText(desc);

        //txtBody.setText();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void AlertDialogAssignMent(String type, String optins, String desc) {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.message_details_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtSubject = message.findViewById(R.id.txtSubject);
        TextView txtlvl = message.findViewById(R.id.txtlvl);
        TextView txtTerms = message.findViewById(R.id.txtTerms);
        TextView txtDesc = message.findViewById(R.id.txtDesc);


        TextView btnExit = message.findViewById(R.id.btnExit);
        txtSubject.setText(type
                + ""
                + optins);
        txtTitle.setVisibility(View.GONE);
        txtlvl.setVisibility(View.GONE);
        txtTerms.setVisibility(View.GONE);
        txtDesc.setText(desc);

        //txtBody.setText();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void AlertDialogRegCourse(String from, String since, String course, String corseName, String degree, String desc) {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.message_details_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtSubject = message.findViewById(R.id.txtSubject);
        TextView txtlvl = message.findViewById(R.id.txtlvl);
        TextView txtTerms = message.findViewById(R.id.txtTerms);
        TextView txtDesc = message.findViewById(R.id.txtDesc);


        TextView btnExit = message.findViewById(R.id.btnExit);
        txtSubject.setText(corseName);
        txtTitle.setText(from);
        txtlvl.setText(since);
        txtTerms.setText("مبحث " + course + "مدرک " + degree);
        txtDesc.setText(desc);

        //txtBody.setText();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

}

    public void removeAds(final int position, final String type, int id) {

        String url = "http://arayesh.myzibadasht.ir/api/ad_del";
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("type", type);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    switch (type) {
                        case "recruiments":
                            recruiments.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, recruiments.size());

                            break;

                        case "assignmets":
                            assignments.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, assignments.size());
                            break;
                        case "discount_ads":
                            disscounts.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, disscounts.size());

                            break;

                        case "workshops":
                            workshops.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, workshops.size());
                            break;

                        case "brides":
                            brides.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, brides.size());
                            break;

                        case "courses":
                            regcourses.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, regcourses.size());
                            break;
                    }
                } catch (Exception e) {
                }
            }
        });

    }


}