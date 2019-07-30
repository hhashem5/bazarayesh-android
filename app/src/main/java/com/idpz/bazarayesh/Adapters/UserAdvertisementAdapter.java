package com.idpz.bazarayesh.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idpz.bazarayesh.MainActivity;
import com.idpz.bazarayesh.Models.AdWorkShop.Ad;
import com.idpz.bazarayesh.R;
import com.idpz.bazarayesh.UserAdvertisementActivity;
import com.idpz.bazarayesh.Utils.Tools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.BaseActivity.RIGHT_TO_LEFT;
import static com.idpz.bazarayesh.UserAdvertisementActivity.ex;
import static maes.tech.intentanim.CustomIntent.customType;


public class UserAdvertisementAdapter extends RecyclerView.Adapter<UserAdvertisementAdapter.NewsViewHolder> {


    private int lastPosition = -1;
    private Context context;
    Activity activity;
    Tools tools;
    Typeface typeface;
    int layout;

    String key;
    private Dialog internetDialog;


    List<Ad> workshops;
    List<com.idpz.bazarayesh.Models.AdDisscount.Ad> disscounts;
    List<com.idpz.bazarayesh.Models.AdAssignment.Ad> assignments;
    List<com.idpz.bazarayesh.Models.AdRecruiment.Ad> recruiments;
    List<com.idpz.bazarayesh.Models.AdBride.Ad> brides;
    List<com.idpz.bazarayesh.Models.AdRegCourse.Ad> regcourses;


    public UserAdvertisementAdapter(List<Ad> workshops, Context context, String layout, String key) {
        this.workshops = workshops;
        this.context = context;
        this.typeface = typeface;
        this.activity = (Activity) context;

        this.key = key;
        tools = new Tools(context);
    }

    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdDisscount.Ad> disscounts, String layout, Context context, int lastPosition, int a, String key) {
        this.disscounts = disscounts;

        this.context = context;
        tools = new Tools(context);
        this.key = key;
    }


    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdBride.Ad> brides, String layout, Context context, int lastPosition, String key) {
        this.brides = brides;

        this.context = context;
        tools = new Tools(context);
        this.key = key;

    }


    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdAssignment.Ad> assignments, String layout, Context context, String key) {
        this.assignments = assignments;
        this.context = context;
        tools = new Tools(context);
        this.key = key;

    }

    public UserAdvertisementAdapter(List<com.idpz.bazarayesh.Models.AdRecruiment.Ad> recruiments, Context context, String layout, int lastPosition, String key) {
        this.recruiments = recruiments;

        this.context = context;
        tools = new Tools(context);
        this.key = key;


    }

    public UserAdvertisementAdapter(int a, List<com.idpz.bazarayesh.Models.AdRegCourse.Ad> regcourses, Context context, String layout, String key) {
        this.regcourses = regcourses;
        this.context = context;
        tools = new Tools(context);
        this.key = key;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_item_adapter, parent, false);


        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {


        if (ex == 1)
            holder.btnExtend.setVisibility(View.VISIBLE);

         else
            holder.btnExtend.setVisibility(View.GONE);


        switch (key) {


            case "workshop":

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

                            deleteDialog(position, "workshops", workshops.get(position).getId());
                            //  removeAds(position, "workshops",workshops.get(position).getId() );

                        }
                    });

                    holder.btnExtend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ExtendDilog( workshops.get(position).getId(),"Workshops",position,workshops.get(position).getMemId());
                        }
                    });

                } catch (Exception e) {
                }



                break;


            case "recruiment":
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
                                deleteDialog(position, "recruiments", recruiments.get(position).getId());

                                //    removeAds(position, "recruiments", recruiments.get(position).getId());

                            }
                        });

                        holder.btnExtend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ExtendDilog( recruiments.get(position).getId(),"Recruiment",position,recruiments.get(position).getMemId());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
                break;


            case "regcourse":
                try {


                    if (regcourses != null) {


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
                                deleteDialog(position, "courses", regcourses.get(position).getId());

                                //removeAds(position, "courses", regcourses.get(position).getId());

                            }
                        });

                        holder.btnExtend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ExtendDilog( regcourses.get(position).getId(),"Reg_Course",position,regcourses.get(position).getMemId());
                            }
                        });
                    }
                } catch (Exception e) {
                }
                break;
            case "assignment":

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
                            deleteDialog(position, "assignments", assignments.get(position).getId());

                            //   removeAds(position, "assignments", assignments.get(position).getId());

                        }
                    });

                    holder.btnExtend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ExtendDilog( assignments.get(position).getId(),"Assignment",position,assignments.get(position).getMemId());
                        }
                    });

                } catch (Exception e) {
                }
                break;
            case "discount":
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
                                deleteDialog(position, "discount_ads", disscounts.get(position).getId());

                                // removeAds(position, "Discount_ads", disscounts.get(position).getId());

                            }
                        });

                        holder.btnExtend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ExtendDilog( disscounts.get(position).getId(),"Discount_ads",position,disscounts.get(position).getMemId());
                            }
                        });

                    }
                } catch (Exception e) {

                }

                break;
            case "bride":
                try {


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
                            //  removeAds(position, "brides", brides.get(position).getId());
                            deleteDialog(position, "brides", brides.get(position).getId());


                        }
                    });

                    holder.btnExtend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ExtendDilog( brides.get(position).getId(),"Bride",position,brides.get(position).getMemId());
                        }
                    });


                } catch (Exception e) {
                }
                break;

        }


    }

    @Override
    public int getItemCount() {
        switch (key) {
            case "assignment":
                return assignments.size();
            case "recruiment":
                return recruiments.size();

            case "regcourse":
                return regcourses.size();

            case "workshop":
                return workshops.size();

            case "discount":


                return disscounts.size();

            case "bride":
                return brides.size();

        }
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        // tarife view ha

        TextView text1, text2;

        RelativeLayout rel;

        ImageView trash;

        Button btnExtend;

        public NewsViewHolder(View itemView) {
            // bind karadne view ha
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            trash = itemView.findViewById(R.id.trash);
            rel = itemView.findViewById(R.id.rel);
            btnExtend = itemView.findViewById(R.id.btnExtend);


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

                        case "assignments":
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

    public void deleteDialog(final int position, final String type, final int id) {
        internetDialog = new Dialog(context);
        internetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        internetDialog.setContentView(R.layout.delete_dialog);
        internetDialog.setCancelable(false);
        internetDialog.show();
        TextView btnOk = internetDialog.findViewById(R.id.ok);
        TextView btnCancel = internetDialog.findViewById(R.id.cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAds(position, type, id);
                successDialog("آگهی مورد نظر با موفقیت حذف شد.");
                internetDialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetDialog.dismiss();
            }
        });
        internetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void extendFunc(int ad_id, final String ad_type, final int position,final int mem_id ) {
        String url = tools.baseUrl + "ads_extende";
        RequestParams params = new RequestParams();
        params.put("member_id",mem_id );
        params.put("ad_id",ad_id);
        params.put("ad_type", ad_type);
        params.put("api_token", tools.getSharePrf("api_token"));
        params.put("APP_KEY", "bazarayesh:barber:11731e11b");

        tools.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    switch (ad_type) {
                        case "Recruiment":
                            recruiments.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, recruiments.size());

                            break;

                        case "Assignment":
                            assignments.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, assignments.size());
                            break;
                        case "Discount_ads":
                            disscounts.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, disscounts.size());

                            break;

                        case "Workshops":
                            workshops.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, workshops.size());
                            break;

                        case "Bride":
                            brides.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, brides.size());
                            break;

                        case "Reg_Course":
                            regcourses.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, regcourses.size());
                            break;
                    }

                    successDialog("با موفقیت تمدید شد.");
                } catch (Exception e) {
                }
            }
        });


    }


    public void ExtendDilog(final int ad_id, final String ad_type, final int position, final int mem_id ) {
        internetDialog = new Dialog(context);
        internetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        internetDialog.setContentView(R.layout.extend_dialog);
        internetDialog.setCancelable(false);
        internetDialog.show();
        TextView btnOk = internetDialog.findViewById(R.id.ok);
        TextView btnCancel = internetDialog.findViewById(R.id.cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extendFunc(ad_id,ad_type,position,mem_id);
                internetDialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetDialog.dismiss();
            }
        });
        internetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void successDialog(String text) {

        final Dialog message = new Dialog(context);
        message.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        message.setContentView(R.layout.success_dialog);
        message.setCancelable(true);
        message.show();
        TextView txtTitle = message.findViewById(R.id.txtTitle);
        TextView txtBody = message.findViewById(R.id.txtBody);
        TextView btnExit = message.findViewById(R.id.btnExit);
        txtTitle.setText("پیام");
        txtBody.setText(text);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message.dismiss();
            }
        });
        message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


}